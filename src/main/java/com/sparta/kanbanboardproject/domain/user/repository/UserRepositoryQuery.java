package com.sparta.kanbanboardproject.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;
}
