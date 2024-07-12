package com.sparta.kanbanboardproject.domain.board.controller;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.service.BoardService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "BoardController")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<BoardResponseDto> addBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.addBoard(userDetails.getUser(), requestDto));
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> getAllBoard() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.getAllBoard());
    }

    @GetMapping("/boards/{board_id}")
    public ResponseEntity<BoardResponseDto> getBoard(
            @PathVariable Long board_id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.getBoard(board_id));
    }

    @PostMapping("/boards/{board_id}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id,
            @RequestBody BoardRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.updateBoard(userDetails.getUser(), board_id, requestDto));
    }

    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity<BoardResponseDto> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.deleteBoard(userDetails.getUser(), board_id));
    }

    @PostMapping("/boards/{board_id}/invite/{user_id}")
    public ResponseEntity<String> userInviteToBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long user_id,
            @PathVariable Long board_id) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.inviteCollaborator(userDetails.getUser(), user_id, board_id));
    }
}
