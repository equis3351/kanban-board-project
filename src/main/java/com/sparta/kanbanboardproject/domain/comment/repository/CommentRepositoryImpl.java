package com.sparta.kanbanboardproject.domain.comment.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.card.entity.QCard;
import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import com.sparta.kanbanboardproject.domain.comment.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<Comment> findAllByCardId(Long cardId, Sort createdAt){
        QComment comment=QComment.comment;
        QCard card=QCard.card;

        return jpaQueryFactory
                .select(comment)
                .from(comment)
                .join(comment.card, card).fetchJoin()
                .where(comment.card.id.eq(cardId))
                .fetch();
    }
}
