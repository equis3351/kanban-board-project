package com.sparta.kanbanboardproject.domain.user.repository;

public interface CustomCollaboratorRepository {
    boolean existsByUserId(Long invitedUserId);
}
