package com.sparta.kanbanboardproject.domain.user.repository.collaborator;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.user.entity.QCollaborator;
import com.sparta.kanbanboardproject.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CollaboratorRepositoryImpl implements CustomCollaboratorRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByUserId(Long invitedUserId) {
        QCollaborator collaborator=QCollaborator.collaborator;

        Integer fetchFirst = jpaQueryFactory.selectOne()
                .from(collaborator)
                .where(collaborator.user.id.eq(invitedUserId))
                .fetchFirst();
        return fetchFirst != null;
    }
}
