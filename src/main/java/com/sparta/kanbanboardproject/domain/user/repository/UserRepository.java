package com.sparta.kanbanboardproject.domain.user.repository;

import com.sparta.kanbanboardproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

//    Optional<User> findByRefreshToken(String refreshToken);
}
