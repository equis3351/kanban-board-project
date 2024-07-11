package com.sparta.kanbanboardproject.domain.card.service;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;
import com.sparta.kanbanboardproject.domain.card.dto.CardResponseDto;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.repository.CardRepository;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.progress.repository.ProgressRepository;
import com.sparta.kanbanboardproject.domain.user.entity.Worker;
import com.sparta.kanbanboardproject.domain.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CardService")
public class CardService {

    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final ProgressRepository progressRepository;
    private final WorkerRepository workerRepository;

    // 카드 목록 조회
    public List<Card> getAllByBoard(Long boardId) {
        Board board = getBoardById(boardId);

        return cardRepository.findAllByBoard(board);
    }

    // 카드 작업자별 조회
    public List<Card> getAllByBoardAndWorker(Long boardId, Long workerId) {
        Board board = getBoardById(boardId);
        Worker worker = workerRepository.findById(workerId).orElseThrow(
            () -> new IllegalArgumentException("Not Found Worker"));

//        return cardRepository.findAllByBoardAndWorker(board, worker);
        return null;
    }

    // 카드 상태별 조회
    public List<Card> getAllByBoardAndProgress(Long boardId, Long progressId) {
        Board board = getBoardById(boardId);
        Progress progress = getProgressById(progressId);

        return cardRepository.findAllByBoardAndProgress(board, progress);
    }

    // 카드 생성
    public CardResponseDto createCard(Long boardId, Long progressId, String title) {
        Board board = getBoardById(boardId);
        Progress progress = getProgressById(progressId);

        Card card = Card.builder()
            .title(title)
            .board(board)
            .progress(progress)
            .build();
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 제목 수정
    public CardResponseDto updateCardTitle(Long boardId, Long progressId, Long cardId, String title) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        card.updateTitle(title);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 내용 수정
    public CardResponseDto updateCardContent(Long boardId, Long progressId, Long cardId, String content) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        card.updateContent(content);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 마감일 수정
    public CardResponseDto updateCardDueDate(Long boardId, Long progressId, Long cardId, Date dueDate) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        card.updateDueDate(dueDate);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 작업자 지정
    public CardResponseDto updateCardWorker(Long boardId, Long progressId, Long cardId, Long workerId) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        // 작업자 지정 - X
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 삭제
    public void deleteCard(Long boardId, Long progressId, Long cardId) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        cardRepository.delete(card);
    }

    // 카드 순서 이동
    public CardResponseDto updateCardSequence(Long boardId, Long progressId, Long cardId, Long sequence, Long updateProgressId) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        // 순서 지정 - X
        if (updateProgressId != null) {
            Progress progress = getProgressById(updateProgressId);
            card.updateProgress(progress);
        }
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 상세 조회
    public CardResponseDto getCardById(Long boardId, Long progressId, Long cardId) {
        Card card = getCardByIdAndBoardIdAndProgressId(boardId, progressId, cardId);

        return new CardResponseDto(card);
    }

    // 보드 확인
    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
            () -> new IllegalArgumentException("Not Found Board"));
    }

    // 컬럼 확인
    private Progress getProgressById(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(
            () -> new IllegalArgumentException("Not Found Progress"));
    }

    // 카드 확인
    private Card getCardByIdAndBoardIdAndProgressId(Long boardId, Long progressId, Long cardId) {
        return cardRepository.findByBoardIdAndProgressIdAndId(boardId, progressId, cardId).orElseThrow(
            () -> new IllegalArgumentException("Not Found Card"));
    }

}
