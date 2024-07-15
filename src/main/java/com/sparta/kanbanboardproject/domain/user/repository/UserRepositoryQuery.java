package com.sparta.kanbanboardproject.domain.user.repository;

import com.sparta.kanbanboardproject.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryQuery {
    Optional<User> searchUser(String username);
}
