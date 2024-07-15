package com.sparta.kanbanboardproject.domain.progress.repository;

import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Long countByBoardId(Long boardId);

    List<Progress> findByBoardIdAndSequenceNumberGreaterThan(Long boardId, Long sequenceNum);

    List<Progress> findByBoardId(Long boardId);

    Optional<Progress> findByBoardIdAndSequenceNumber(Long boardId, Long sequenceNum);

    List<Progress> findByBoardIdOrderBySequenceNumber(Long boardId);

}
