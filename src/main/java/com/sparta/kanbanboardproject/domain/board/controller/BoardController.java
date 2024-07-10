package com.sparta.kanbanboardproject.domain.board.controller;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponseDto addBoard(@RequestBody BoardRequestDto requestDto){
        return boardService.addBoard(requestDto);
    }

    @GetMapping("/boards/{id}")
    public List<BoardResponseDto> getAllBoard(){
        return boardService.getAllBoard();
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PostMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        return boardService.updateBoard(id,requestDto);
    }

    @DeleteMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id){
        return boardService.deleteBoard(id);
    }
}
