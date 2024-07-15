package com.sparta.kanbanboardproject.domain.user.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sparta.kanbanboardproject.domain.user.entity.QCollaborator.collaborator;

@Repository
@RequiredArgsConstructor
public class CollaboratorRepositoryImpl implements CustomCollaboratorRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByUserId(Long invitedUserId) {
        Integer fetchFirst = jpaQueryFactory.selectOne()
                .from(collaborator)
                .where(collaborator.user.id.eq(invitedUserId))
                .fetchJoin()
                .fetchFirst();
        return fetchFirst != null;
    }
}
