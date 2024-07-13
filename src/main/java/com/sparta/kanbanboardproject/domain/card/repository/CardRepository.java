package com.sparta.kanbanboardproject.domain.card.repository;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Long countByProgressId(Long progressId);
    List<Card> findByProgressIdAndSequenceNumberGreaterThan(Long progressId, Long sequenceNum);
    Optional<Card> findByProgressIdAndSequenceNumber(Long progressId, Long sequenceNum);

    List<Card> findAllByProgress(Progress progress);

    List<Card> findByProgressBoardId(Long boardId);
}
