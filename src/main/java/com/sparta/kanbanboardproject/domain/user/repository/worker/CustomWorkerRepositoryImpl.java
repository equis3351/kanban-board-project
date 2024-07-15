package com.sparta.kanbanboardproject.domain.user.repository.worker;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.user.entity.QWorker;
import com.sparta.kanbanboardproject.domain.user.entity.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomWorkerRepositoryImpl implements CustomWorkerRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Worker> findAllById(Long workerId) {
        QWorker worker = QWorker.worker;

        return queryFactory
                .selectFrom(worker)
                .where(worker.id.eq(workerId))
                .fetch();
    }
}
