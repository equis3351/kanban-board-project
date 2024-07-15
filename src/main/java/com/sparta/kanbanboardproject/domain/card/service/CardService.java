package com.sparta.kanbanboardproject.domain.card.service;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;
import com.sparta.kanbanboardproject.domain.card.dto.CardPositionRequestDto;
import com.sparta.kanbanboardproject.domain.card.dto.CardResponseDto;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.repository.CardRepository;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.progress.repository.ProgressRepository;
import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import com.sparta.kanbanboardproject.domain.user.repository.CollaboratorRepository;
import com.sparta.kanbanboardproject.domain.user.repository.worker.WorkerRepository;
import com.sparta.kanbanboardproject.global.exception.CustomException;
import com.sparta.kanbanboardproject.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "CardService")
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final ProgressRepository progressRepository;
    private final WorkerRepository workerRepository;
    private final CollaboratorRepository collaboratorRepository;

    // 카드 생성
    public CardResponseDto addCard(Long progressId, String title) {
        Progress progress = getProgressById(progressId);

        Card card = Card.builder()
            .title(title)
            .progress(progress)
            .build();

        Long countCards = cardRepository.countByProgressId(progressId);
        card.increaseSequence(countCards);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 카드 상세 조회
    public CardResponseDto getCard(Long cardId) {
        Card card = getCardById(cardId);

        return new CardResponseDto(card);
    }

    // 카드 목록 조회
    public List<CardResponseDto> getAllByBoard(Long boardId) {
        Board board = getBoardById(boardId);

        return cardRepository.findAll()
            .stream()
            .filter(card -> card.getProgress().getBoard().equals(board))
            .map(CardResponseDto::new)
            .toList();
    }

    // 카드 작업자별 조회
    public List<CardResponseDto> getAllByBoardAndWorker(Long boardId, Long workerId) {
        Board board = getBoardById(boardId);

        return workerRepository.findAllById(workerId)
            .stream()
            .filter(worker -> worker.getCard().getProgress().getBoard().equals(board))
            .map(worker -> new CardResponseDto(worker.getCard()))
            .distinct()
            .collect(Collectors.toList());
    }

    // 카드 상태별 조회
    public List<CardResponseDto> getAllByBoardAndProgress(Long boardId, Long progressId) {
        Board board = getBoardById(boardId);
        Progress progress = getProgressById(progressId);

        return cardRepository.findAllByProgress(progress)
            .stream()
            .filter(card -> card.getProgress().getBoard().equals(board))
            .map(CardResponseDto::new)
            .toList();
    }

    // 카드 제목 수정
    @Transactional
    public CardResponseDto updateCardTitle(Long cardId, String title) {
        Card card = getCardById(cardId);

        card.updateTitle(title);

        return new CardResponseDto(card);
    }

    // 카드 내용 수정
    @Transactional
    public CardResponseDto updateCardContent(Long cardId, String content) {
        Card card = getCardById(cardId);

        card.updateContent(content);

        return new CardResponseDto(card);
    }

    // 카드 마감일자 수정
    @Transactional
    public CardResponseDto updateCardDueDate(Long cardId, Date dueDate) {
        Card card = getCardById(cardId);

        card.updateDueDate(dueDate);

        return new CardResponseDto(card);
    }

    // 카드 작업자 지정
    @Transactional
    public CardResponseDto updateCardWorker(Long cardId, Long userId) {
        Card card = getCardById(cardId);
        Collaborator collaborator = getCollaboratorById(userId);

        validateWorker(card, collaborator);

        card.addWorker(card, collaborator.getUser());

        return new CardResponseDto(card);
    }

    // 카드 순서 이동 (+ 상태 변경)
    public CardResponseDto updateCardPosition(Long cardId, Long progressId, CardPositionRequestDto requestDto) {
        Card card = getCardById(cardId);

        if (requestDto.getNewProgressId() != null) {

            Progress newProgress = getProgressById(requestDto.getNewProgressId());
            card.updateProgress(newProgress);

            List<Card> cardList = cardRepository.findByProgressIdAndSequenceNumberGreaterThan(progressId, card.getSequenceNumber());
            for (Card cd : cardList) {
                cd.decreaseSequence(cd.getSequenceNumber());
            }

            List<Card> newProgressCards = cardRepository.findByProgressIdOrderBySequenceNumber(requestDto.getNewProgressId());
            updateCardSequences(newProgressCards, card, requestDto.getSequenceNum());

        } else {
            List<Card> cards = cardRepository.findByProgressIdOrderBySequenceNumber(progressId);
            updateCardSequences(cards, card, requestDto.getSequenceNum());
        }

        return new CardResponseDto(card);
    }

    // 카드 삭제
    @Transactional
    public void deleteCard(Long progressId, Long cardId) {
        Card card = getCardById(cardId);

        cardRepository.delete(card);

        List<Card> cardList = cardRepository.findByProgressIdAndSequenceNumberGreaterThan(progressId, card.getSequenceNumber());
        for (Card cd : cardList) {
            cd.decreaseSequence(cd.getSequenceNumber());
        }

        cardRepository.saveAll(cardList);
    }

    // 보드 확인
    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_BOARD)
        );
    }

    // 컬럼 확인
    private Progress getProgressById(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_PROGRESS)
        );
    }

    // 카드 확인
    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_CARD)
        );
    }

    // 협력자 확인
    private Collaborator getCollaboratorById(Long userId) {
        return collaboratorRepository.findById(userId).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_COLLABORATOR)
        );
    }

    // 작업자 중복 확인
    private void validateWorker(Card card, Collaborator collaborator) {
        boolean existsWorker = card.getWorkerList().stream()
            .anyMatch(worker -> worker.getUser().equals(collaborator.getUser()));

        if (existsWorker) {
            throw new CustomException(ErrorType.DUPLICATE_CARD_WORKER);
        }
    }

    // 카드 이동
    private void updateCardSequences(List<Card> cards, Card movedCard, Long newSequenceNum) {
        Long currentSequenceNumber = movedCard.getSequenceNumber();

        if (newSequenceNum < 0 || newSequenceNum > cards.size()) {
            throw new CustomException(ErrorType.INVALID_SEQUENCE_NUMBER);
        }

        if (newSequenceNum.equals(currentSequenceNumber)) {
            return;
        }

        // 카드의 순서를 이동
        if (newSequenceNum < currentSequenceNumber) {
            for (Card card : cards) {
                if (card.getSequenceNumber() >= newSequenceNum && card.getSequenceNumber() < currentSequenceNumber) {
                    card.increaseSequence(card.getSequenceNumber());
                }
            }
        } else {
            for (Card card : cards) {
                if (card.getSequenceNumber() > currentSequenceNumber && card.getSequenceNumber() <= newSequenceNum) {
                    card.decreaseSequence(card.getSequenceNumber());
                }
            }
        }

        movedCard.updateSequence(newSequenceNum);
    }

}
