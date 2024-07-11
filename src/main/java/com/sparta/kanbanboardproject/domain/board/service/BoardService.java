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

    public BoardResponseDto addBoard(User user, BoardRequestDto requestDto) {

        if (!user.getRole().equals(UserRoleEnum.MANAGER)) {
            throw new IllegalArgumentException("매니저만 보드 생성이 가능합니다");
        }

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
        if (!(user.getRole().equals(UserRoleEnum.MANAGER))) {
            throw new IllegalArgumentException("매니저만 보드 수정이 가능합니다");
        } else if (!(user.getId().equals(board.getUser().getId()))) {
            throw new IllegalArgumentException("userId가 일치하지 않습니다");
        }//메소드로 뺀다
        board.updateBoard(requestDto);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto deleteBoard(User user, Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        if (!(user.getRole().equals(UserRoleEnum.MANAGER))) {
            throw new IllegalArgumentException("매니저만 보드 삭제가 가능합니다");
        } else if (!(user.getId().equals(board.getUser().getId()))) {
            throw new IllegalArgumentException("userId가 일치하지 않습니다");
        }

        boardRepository.delete(board);

        return new BoardResponseDto(board);
    }
}
