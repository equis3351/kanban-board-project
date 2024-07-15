package com.sparta.kanbanboardproject.domain.comment.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import com.sparta.kanbanboardproject.domain.comment.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.kanbanboardproject.domain.comment.entity.QComment.comment;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;


    public List<Comment> searchCardDesc(Long cardId, Sort createdAt){
        return jpaQueryFactory
                .select(comment)
                .from(comment)
                .where(comment.card.id.eq(cardId))
                .fetchJoin()
                .fetch();
    }
}