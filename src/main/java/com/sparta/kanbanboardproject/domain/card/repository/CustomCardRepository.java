package com.sparta.kanbanboardproject.domain.card.repository;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;

import java.util.List;
import java.util.Optional;

public interface CustomCardRepository {

    Long countByProgressId(Long progressId);

    Optional<Card> findByBoardIdAndProgressIdAndId(Long boardId, Long progressId, Long id);
    Optional<Card> findByProgressIdAndSequenceNumber(Long progressId, Long sequenceNum);

    List<Card> findAllByBoard(Board board);
    List<Card> findAllByBoardAndProgress(Board board, Progress progress);
    List<Card> findByProgressIdAndSequenceNumberGreaterThan(Long progressId, Long sequenceNum);

}
