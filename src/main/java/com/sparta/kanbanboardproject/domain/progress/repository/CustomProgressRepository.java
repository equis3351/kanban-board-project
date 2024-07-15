package com.sparta.kanbanboardproject.domain.progress.repository;

import com.sparta.kanbanboardproject.domain.progress.entity.Progress;

import java.util.List;
import java.util.Optional;

public interface CustomProgressRepository {
    Long countByBoardId(Long boardId);
    List<Progress> findByBoardIdAndSequenceNumberGreaterThan(Long boardId, Long sequenceNum);
    List<Progress> findByBoardId(Long boardId);
    List<Progress> findByBoardIdOrderBySequenceNumber(Long boardId);
}
