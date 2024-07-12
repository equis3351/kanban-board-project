package com.sparta.kanbanboardproject.domain.progress.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparta.kanbanboardproject.domain.progress.entity.QProgress.progress;


@Repository
@RequiredArgsConstructor
public class ProgressRepositoryImpl implements ProgressRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countBoard(Long boardId) {
        return jpaQueryFactory
                .select(progress.count())
                .from(progress)
                .where(progress.board.id.eq(boardId))
                .fetchOne();
    }


    @Override
    public List<Progress> searchBoardAndSeqGreater(Long boardId, Long sequenceNum) {
        return jpaQueryFactory
                .select(progress)
                .from(progress)
                .where(progress.board.id.eq(boardId).and(progress.sequenceNumber.gt(sequenceNum)))
                .fetch();
    }

    @Override
    public List<Progress> searchBoard(Long boardId) {
        return jpaQueryFactory
                .select(progress)
                .from(progress)
                .where(progress.board.id.eq(boardId))
                .fetch();
    }

    @Override
    public Optional<Progress> searchBoardAndSeq(Long boardId, Long sequenceNum) {
        return Optional.ofNullable(jpaQueryFactory
                .select(progress)
                .from(progress)
                .where(progress.board.id.eq(boardId).and(progress.sequenceNumber.eq(sequenceNum)))
                .fetchOne());
    }
}
