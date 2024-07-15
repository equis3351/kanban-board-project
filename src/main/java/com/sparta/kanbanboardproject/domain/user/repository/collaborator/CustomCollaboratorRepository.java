package com.sparta.kanbanboardproject.domain.user.repository.collaborator;

public interface CustomCollaboratorRepository {
    boolean existsByUserId(Long invitedUserId);
}
