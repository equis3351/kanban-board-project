package com.sparta.kanbanboardproject.domain.progress.repository;

import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Long countByBoardId(Long boardId);
}
