package com.sparta.kanbanboardproject.domain.card.controller;

import com.sparta.kanbanboardproject.domain.card.dto.CardCreateRequestDto;
import com.sparta.kanbanboardproject.domain.card.dto.CardResponseDto;
import com.sparta.kanbanboardproject.domain.card.dto.CardUpdateRequestDto;
import com.sparta.kanbanboardproject.domain.card.service.CardService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{board_id}")
public class CardController {

    private final CardService cardService;

    // 카드 목록 조회
    @GetMapping("/cards")
    public ResponseEntity<List<CardResponseDto>> getCardList(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long board_id) {

        List<CardResponseDto> responseDtoList = cardService.getAllByBoard(userDetails.getUser(), board_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 카드 생성
    @PostMapping("/{progress_id}/cards")
    public ResponseEntity<CardResponseDto> createCard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @RequestBody CardCreateRequestDto requestDto) {

        CardResponseDto responseDto = cardService.createCard(userDetails.getUser(), board_id, progress_id, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 카드 수정
    @PutMapping("/{progress_id}/cards/{card_id}")
    public ResponseEntity<CardResponseDto> updateCard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestBody CardUpdateRequestDto requestDto) {

        CardResponseDto responseDto = cardService.updateCard(userDetails.getUser(), board_id, progress_id, card_id, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 삭제
    @DeleteMapping("/{progress_id}/cards/{card_id}")
    public ResponseEntity<String> updateCard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id) {

        cardService.deleteCard(userDetails.getUser(), board_id, progress_id, card_id);

        return ResponseEntity.status(HttpStatus.OK).body("카드가 삭제되었습니다.");
    }

    // 카드 순서 이동
    @PutMapping("/{progress_id}/cards/{card_id}")
    public ResponseEntity<CardResponseDto> updateCardSequence(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id,
        @RequestParam Long sequence) {

        CardResponseDto responseDto = cardService.updateCardSequence(userDetails.getUser(), board_id, progress_id, card_id, sequence);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 상세 조회
    @GetMapping("/{progress_id}/cards/{card_id}")
    public ResponseEntity<CardResponseDto> getCard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long board_id,
        @PathVariable Long progress_id,
        @PathVariable Long card_id) {

        CardResponseDto responseDto = cardService.getCardById(userDetails.getUser(), board_id, progress_id, card_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
