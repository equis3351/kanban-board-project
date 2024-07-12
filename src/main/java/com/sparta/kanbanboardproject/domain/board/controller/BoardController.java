package com.sparta.kanbanboardproject.domain.board.controller;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.service.BoardService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

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

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.getBoard(id));
    }

    @PostMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody BoardRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).
                body(boardService.updateBoard(userDetails.getUser(), id, requestDto));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).
                body(boardService.deleteBoard(userDetails.getUser(), id));
    }

    @PostMapping("/boards/{boardId}/invite/{userId}")
    public ResponseEntity<String> userInviteToBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId,
            @PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(boardService.inviteCollaborator(userDetails.getUser(), userId, boardId));
    }
}
