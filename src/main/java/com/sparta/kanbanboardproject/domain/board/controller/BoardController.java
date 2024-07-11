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
    public BoardResponseDto addBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto requestDto) {
        return boardService.addBoard(userDetails.getUser(), requestDto);
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getAllBoard() {
        return boardService.getAllBoard();
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(userDetails.getUser(), id, requestDto);
    }

    @DeleteMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return boardService.deleteBoard(userDetails.getUser(), id);
    }
}
