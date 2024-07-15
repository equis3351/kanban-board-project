package com.sparta.kanbanboardproject.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.entity.QCard;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Card> findAllByProgress(Progress progress) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(card.progress.eq(progress))
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

    @Override
    public List<Card> findByProgressIdOrderBySequenceNumber(Long progressId) {
        QCard card = QCard.card;

        return queryFactory.selectFrom(card)
            .where(card.progress.id.eq(progressId))
            .orderBy(card.sequenceNumber.asc())
            .fetch();
    }
}
