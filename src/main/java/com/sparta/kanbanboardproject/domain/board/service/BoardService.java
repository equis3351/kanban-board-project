package com.sparta.kanbanboardproject.domain.board.service;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.board.dto.BoardResponseDto;
import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;

import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.domain.user.repository.CollaboratorRepository;
import com.sparta.kanbanboardproject.domain.user.repository.UserRepository;
import com.sparta.kanbanboardproject.global.exception.CustomException;
import com.sparta.kanbanboardproject.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CollaboratorRepository collaboratorRepository;


    //보드 생성
    public BoardResponseDto addBoard(User user, BoardRequestDto requestDto) {

        Board board = new Board(requestDto, user);
        Collaborator collaborator = new Collaborator(user, board);
        boardRepository.save(board);
        collaboratorRepository.save(collaborator);

        return new BoardResponseDto(board);
    }

    //보드 조회
    public BoardResponseDto getBoard(Long id) {
        Board board = findByIdBoard(id);

        return new BoardResponseDto(board);
    }

    //모든 보드 조회
    public List<BoardResponseDto> getAllBoard() {
        List<Board> board = boardRepository.findAll();

        return board.stream().map(BoardResponseDto::new).toList();
    }

    //보드 수정
    public BoardResponseDto updateBoard(User user, Long id, BoardRequestDto requestDto) {
        Board board = findByIdBoard(id);
        checkUserId(user, board);

        board.updateBoard(requestDto);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    //보드 삭제
    public BoardResponseDto deleteBoard(User user, Long id) {
        Board board = findByIdBoard(id);
        checkUserId(user, board);

        boardRepository.delete(board);

        return new BoardResponseDto(board);
    }

    //협력자 초대
    public String inviteCollaborator(User user, Long invitedUserId, Long boardId) {
        User invitedUser = userRepository.findById(invitedUserId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOARD)
        );

        checkUserId(user, board);

        if (collaboratorRepository.existsByUserId(invitedUserId)) {
            throw new IllegalArgumentException("이미 초대 되어있는 사용자입니다.");
        }

        Collaborator collaborator = new Collaborator(invitedUser, board);

        collaboratorRepository.save(collaborator);

        return invitedUser.getUsername() + "님 초대 완료";
    }

    //유저 아이디 체크
    private void checkUserId(User user, Board board) {
        if (!(user.getId().equals(board.getUser().getId()))) {
            throw new IllegalArgumentException("userId가 일치하지 않습니다");
        }
    }

    //보드 id로 조회
    private Board findByIdBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOARD)
        );
    }

}
