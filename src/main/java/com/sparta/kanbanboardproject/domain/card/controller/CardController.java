package com.sparta.kanbanboardproject.domain.card.controller;

import com.sparta.kanbanboardproject.domain.card.dto.*;
import com.sparta.kanbanboardproject.domain.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "CardController")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping("/progresses/{progress_id}/cards")
    public ResponseEntity<CardResponseDto> addCard(
        @PathVariable Long progress_id,
        @Valid @RequestBody CardTitleRequestDto requestDto) {

        CardResponseDto responseDto = cardService.addCard(progress_id, requestDto.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 카드 상세 조회
    @GetMapping("/cards/{card_id}")
    public ResponseEntity<CardResponseDto> getCard(
        @PathVariable Long card_id) {

        CardResponseDto responseDto = cardService.getCard(card_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 목록 조회
    @GetMapping("/cards/boards/{board_id}")
    public ResponseEntity<List<CardResponseDto>> getCardList(
        @PathVariable Long board_id) {

        List<CardResponseDto> responseDtoList = cardService.getAllByBoard(board_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 카드 작업자별 조회
    @GetMapping("/cards/boards/{board_id}/workers/{worker_id}")
    public ResponseEntity<List<CardResponseDto>> getCardListByWorkerList(
        @PathVariable Long board_id,
        @PathVariable Long worker_id) {

        List<CardResponseDto> responseDtoList = cardService.getAllByBoardAndWorker(board_id, worker_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 카드 상태별 조회
    @GetMapping("/cards/boards/{board_id}/progresses/{progress_id}")
    public ResponseEntity<List<CardResponseDto>> getCardListByProgress(
        @PathVariable Long board_id,
        @PathVariable Long progress_id) {

        List<CardResponseDto> responseDtoList = cardService.getAllByBoardAndProgress(board_id, progress_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 카드 제목 수정
    @PutMapping("/cards/{card_id}/title")
    public ResponseEntity<CardResponseDto> updateCardTitle(
        @PathVariable Long card_id,
        @Valid @RequestBody CardTitleRequestDto requestDto) {

        CardResponseDto responseDto = cardService.updateCardTitle(card_id, requestDto.getTitle());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 내용 수정
    @PutMapping("/cards/{card_id}/content")
    public ResponseEntity<CardResponseDto> updateCardContent(
        @PathVariable Long card_id,
        @Valid @RequestBody CardContentRequestDto requestDto) {

        CardResponseDto responseDto = cardService.updateCardContent(card_id, requestDto.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 마감일자 수정
    @PutMapping("/cards/{card_id}/due-date")
    public ResponseEntity<CardResponseDto> updateCardDueDate(
        @PathVariable Long card_id,
        @Valid @RequestBody CardDueDateRequestDto requestDto) {

        CardResponseDto responseDto = cardService.updateCardDueDate(card_id, requestDto.getDueDate());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 작업자 지정
    @PutMapping("/cards/{card_id}/worker")
    public ResponseEntity<CardResponseDto> updateCardWorker(
        @PathVariable Long card_id,
        @Valid @RequestBody CardWorkerRequestDto requestDto) {

        CardResponseDto responseDto = cardService.updateCardWorker(card_id, requestDto.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 순서 이동 (+ 상태 변경)
    @PutMapping("/cards/{card_id}/progresses/{progress_id}/position")
    public ResponseEntity<CardResponseDto> updateCardPosition(
        @PathVariable Long card_id,
        @PathVariable Long progress_id,
        @Valid @RequestBody CardPositionRequestDto requestDto) {

        CardResponseDto responseDto = cardService.updateCardPosition(card_id, progress_id, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 삭제
    @DeleteMapping("/progresses/{progress_id}/cards/{card_id}")
    public ResponseEntity<String> deleteCard(
        @PathVariable Long progress_id,
        @PathVariable Long card_id) {

        cardService.deleteCard(progress_id, card_id);

        return ResponseEntity.status(HttpStatus.OK).body("카드가 삭제되었습니다.");
    }

}
