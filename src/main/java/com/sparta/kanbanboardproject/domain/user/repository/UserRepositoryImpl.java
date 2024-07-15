package com.sparta.kanbanboardproject.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sparta.kanbanboardproject.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> searchUser(String username) {
        return Optional.ofNullable(jpaQueryFactory
                .select(user)
                .from(user)
                .where(user.username.eq(username))
                .fetchOne());
    }

}
