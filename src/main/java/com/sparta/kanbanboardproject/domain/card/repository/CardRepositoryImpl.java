package com.sparta.kanbanboardproject.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.entity.QCard;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements CustomCardRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countByProgressId(Long progressId) {
        QCard card = QCard.card;

        return queryFactory
                .select(card.count())
                .from(card)
                .where(card.progress.id.eq(progressId))
                .fetchOne();
    }

    @Override
    public Optional<Card> findByBoardIdAndProgressIdAndId(Long boardId, Long progressId, Long id) {
        QCard card = QCard.card;

        Card foundCard = queryFactory
                .selectFrom(card)
                .where(card.board.id.eq(boardId)
                        .and(card.progress.id.eq(progressId))
                        .and(card.id.eq(id)))
                .fetchOne();

        return Optional.ofNullable(foundCard);
    }

    @Override
    public Optional<Card> findByProgressIdAndSequenceNumber(Long progressId, Long sequenceNum) {
        QCard card = QCard.card;

        Card foundCard = queryFactory
                .selectFrom(card)
                .where(card.progress.id.eq(progressId).and(card.sequenceNumber.eq(sequenceNum)))
                .fetchOne();

        return Optional.ofNullable(foundCard);
    }

    @Override
    public List<Card> findAllByBoard(Board board) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(card.board.eq(board))
                .fetch();
    }

    @Override
    public List<Card> findAllByBoardAndProgress(Board board, Progress progress) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(card.board.eq(board).and(card.progress.eq(progress)))
                .fetch();
    }

    @Override
    public List<Card> findByProgressIdAndSequenceNumberGreaterThan(Long progressId, Long sequenceNum) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(card.progress.id.eq(progressId).and(card.sequenceNumber.gt(sequenceNum)))
                .fetch();
    }
}
