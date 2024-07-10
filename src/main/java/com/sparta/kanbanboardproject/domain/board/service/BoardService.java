package com.sparta.kanbanboardproject.domain.board.service;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponseDto addBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow();

        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> getAllBoard(){
        List<Board> board = boardRepository.findAll();

        return board.stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.UpdateBoard(requestDto);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow();

        boardRepository.delete(board);

        return new BoardResponseDto(board);
    }
}
