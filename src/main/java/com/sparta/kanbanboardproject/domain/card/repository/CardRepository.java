package com.sparta.kanbanboardproject.domain.card.repository;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByBoardIdAndProgressIdAndId(Long boardId, Long progressId, Long id);

    List<Card> findAllByBoard(Board board);
//    List<Card> findAllByBoardAndWorker(Board board, Worker worker);
    List<Card> findAllByBoardAndProgress(Board board, Progress progress);
}
