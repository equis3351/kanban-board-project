package com.sparta.kanbanboardproject.domain.progress.repository;

import com.sparta.kanbanboardproject.domain.progress.entity.Progress;

import java.util.List;
import java.util.Optional;

public interface ProgressRepositoryQuery {
    Long countBoard(Long boardId);
    List<Progress> searchBoardAndSeqGreater(Long boardId, Long sequenceNum);
    List<Progress> searchBoard(Long boardId);
    Optional<Progress> searchBoardAndSeq(Long boardId, Long sequenceNum);
}
