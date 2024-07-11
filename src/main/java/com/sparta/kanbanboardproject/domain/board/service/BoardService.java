package com.sparta.kanbanboardproject.domain.board.service;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;

import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.domain.user.entity.UserRoleEnum;
import com.sparta.kanbanboardproject.domain.user.repository.CollaboratorRepository;
import com.sparta.kanbanboardproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CollaboratorRepository collaboratorRepository;

    public BoardService(UserRepository userRepository, BoardRepository boardRepository, CollaboratorRepository collaboratorRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.collaboratorRepository = collaboratorRepository;
    }

    public void checkUserId(User user, Board board) {
        if (!(user.getId().equals(board.getUser().getId()))) {
            throw new IllegalArgumentException("userId가 일치하지 않습니다");
        }
    }


    public BoardResponseDto addBoard(User user, BoardRequestDto requestDto) {

        Board board = new Board(requestDto, user);
        Collaborator collaborator = new Collaborator(user, board);
        boardRepository.save(board);
        collaboratorRepository.save(collaborator);

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
        checkUserId(user, board);

        board.updateBoard(requestDto);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto deleteBoard(User user, Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        checkUserId(user, board);

        boardRepository.delete(board);

        return new BoardResponseDto(board);
    }

    public String inviteCollaborator(User user, Long invitedUserId, Long boardId) {
        User invitedUser = userRepository.findById(invitedUserId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();

        checkUserId(user, board);

        if (collaboratorRepository.existsByUserId(invitedUserId)) {
            throw new IllegalArgumentException("이미 초대 되어있는 사용자입니다.");
        }

        Collaborator collaborator = new Collaborator(invitedUser, board);

        collaboratorRepository.save(collaborator);

        return invitedUser.getUsername() + "님 초대 완료";
    }
}
