package com.sparta.kanbanboardproject.domain.board.service;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;

import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.domain.user.entity.UserRoleEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void checkUserId(User user,Board board) {
        if (!(user.getId().equals(board.getUser().getId()))) {
            throw new IllegalArgumentException("userId가 일치하지 않습니다");
        }
    }



    public BoardResponseDto addBoard(User user, BoardRequestDto requestDto) {

        Board board = new Board(requestDto, user);
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();

        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> getAllBoard() {
        List<Board> board = boardRepository.findAll();

        return board.stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto updateBoard(User user, Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow();
        checkUserId(user,board);

        board.updateBoard(requestDto);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto deleteBoard(User user, Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        checkUserId(user,board);

        boardRepository.delete(board);

        return new BoardResponseDto(board);
    }
}
