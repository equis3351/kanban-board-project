package com.sparta.kanbanboardproject.domain.card.controller;

import com.sparta.kanbanboardproject.domain.card.dto.CardResponseDto;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{board_id}")
public class CardController {

    private final CardService cardService;

    // 카드 목록 조회
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getCardList(
        @PathVariable Long board_id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.getAllByBoard(board_id));
    }

    // 카드 작업자별 조회
    @GetMapping("/cards/workers/{worker_id}")
    public ResponseEntity<List<Card>> getCardListByWorker(
        @PathVariable Long board_id,
        @PathVariable Long worker_id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.getAllByBoardAndWorker(board_id, worker_id));
    }

    // 카드 상태별 조회
    @GetMapping("/cards/progresses/{progress_id}")
    public ResponseEntity<List<Card>> getCardListByProgress(
        @PathVariable Long board_id,
        @PathVariable Long progress_id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.getAllByBoardAndProgress(board_id, progress_id));
    }

    // 카드 생성
    @PostMapping("/progresses/{progress_id}/cards")
    public ResponseEntity<CardResponseDto> createCard(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @RequestParam String title) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(cardService.createCard(board_id, progress_id, title));
    }

    // 카드 제목 수정
    @PutMapping("/{progress_id}/cards/{card_id}/title")
    public ResponseEntity<CardResponseDto> updateCardTitle(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestParam String title) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.updateCardTitle(board_id, progress_id, card_id, title));
    }

    // 카드 내용 수정
    @PutMapping("/{progress_id}/cards/{card_id}/content")
    public ResponseEntity<CardResponseDto> updateCardContent(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestParam String content) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.updateCardContent(board_id, progress_id, card_id, content));
    }

    // 카드 마감일 수정
    @PutMapping("/{progress_id}/cards/{card_id}/due-date")
    public ResponseEntity<CardResponseDto> updateCardDueDate(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestParam Date dueDate) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.updateCardDueDate(board_id, progress_id, card_id, dueDate));
    }

    // 카드 작업자 지정
    @PutMapping("/{progress_id}/cards/{card_id}/worker")
    public ResponseEntity<CardResponseDto> updateCardWorker(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestParam Long worker_id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.updateCardWorker(board_id, progress_id, card_id, worker_id));
    }

    // 카드 삭제
    @DeleteMapping("/progresses/{progress_id}/cards/{card_id}")
    public ResponseEntity<String> updateCard(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id) {

        cardService.deleteCard(board_id, progress_id, card_id);

        return ResponseEntity.status(HttpStatus.OK)
            .body("카드가 삭제되었습니다.");
    }

    // 카드 순서 이동
    @PutMapping("/progresses/{progress_id}/cards/{card_id}/sequence")
    public ResponseEntity<CardResponseDto> updateCardSequence(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestParam Long prevSeq,
        @RequestParam Long nextSeq,
        @RequestParam(required = false) Long updateProgressId) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.updateCardSequence(board_id, progress_id, card_id, prevSeq, nextSeq, updateProgressId));
    }

    // 카드 상세 조회
    @GetMapping("/progresses/{progress_id}/cards/{card_id}")
    public ResponseEntity<CardResponseDto> getCard(
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cardService.getCardById(board_id, progress_id, card_id));
    }

}
