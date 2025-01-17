package com.sparta.kanbanboardproject.domain.user.repository.collaborator;

import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long>, CustomCollaboratorRepository {
}
