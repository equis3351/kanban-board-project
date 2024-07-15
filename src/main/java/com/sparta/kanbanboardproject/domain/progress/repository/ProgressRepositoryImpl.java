package com.sparta.kanbanboardproject.domain.progress.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.progress.entity.QProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparta.kanbanboardproject.domain.board.entity.QBoard.board;
import static com.sparta.kanbanboardproject.domain.progress.entity.QProgress.progress;


@Repository
@RequiredArgsConstructor
public class ProgressRepositoryImpl implements CustomProgressRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countByBoardId(Long boardId) {
        return jpaQueryFactory
                .select(progress.id.count())
                .from(progress)
                .where(progress.board.id.eq(boardId))
                .fetchOne();
    }


    @Override
    public List<Progress> findByBoardIdAndSequenceNumberGreaterThan(Long boardId, Long sequenceNum) {
        return jpaQueryFactory
                .select(progress)
                .from(progress)
                .where(progress.board.id.eq(boardId).and(progress.sequenceNumber.gt(sequenceNum)))
                .fetch();
    }

    @Override
    public List<Progress> findByBoardId(Long boardId) {
        return jpaQueryFactory
                .select(progress)
                .from(progress)
                .where(progress.board.id.eq(boardId))
                .fetch();
    }


    @Override
    public List<Progress> findByBoardIdOrderBySequenceNumber(Long boardId) {
        return jpaQueryFactory.selectFrom(progress)
                .where(progress.board.id.eq(boardId))
                .orderBy(progress.sequenceNumber.asc())
                .fetch();
    }
}
