package com.sparta.kanbanboardproject.domain.progress.service;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressMoveRequestDto;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressResponseDto;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.progress.repository.ProgressRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.global.exception.CustomException;
import com.sparta.kanbanboardproject.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "ProgressService")
@Service
@RequiredArgsConstructor
public class ProgressService {

    private final BoardRepository boardRepository;
    private final ProgressRepository progressRepository;

    @Transactional
    public ProgressResponseDto addProgress(Long boardId, ProgressCreateRequestDto requestDto, User user) {

        Board board = getBoard(boardId);

        validatedOwner(board, user);

        List<Progress> progressList = progressRepository.findByBoardId(boardId);
        for (Progress pr : progressList) {
            equalStatusName(pr, requestDto);
        }

        Progress progress = new Progress(requestDto, board);

        Long countColumns = progressRepository.countByBoardId(boardId);

        progress.updateSequence(countColumns + 1);

        progressRepository.save(progress);

        return new ProgressResponseDto(progress);
    }

    public void deleteProgress(Long boardId, Long progressId, User user) {

        Board board = getBoard(boardId);

        validatedOwner(board, user);

        Progress progress = getProgress(progressId);

        progressRepository.delete(progress);

        List<Progress> progressList = progressRepository.findByBoardIdAndSequenceNumberGreaterThan(boardId, progress.getSequenceNumber());
        for (Progress pr : progressList) {
            pr.decreaseSequence();
        }

        progressRepository.saveAll(progressList);
    }

    @Transactional
    public ProgressResponseDto moveProgress(Long boardId, Long progressId, ProgressMoveRequestDto requestDto, User user) {

        Board board = getBoard(boardId);

        validatedOwner(board, user);

        Progress changedProgress = getProgress(progressId);

        Long newSequenceNumber = requestDto.getSequenceNumber();
        Long currentSequenceNumber = changedProgress.getSequenceNumber();

        Progress changingProgress = progressRepository.findByBoardIdAndSequenceNumber(boardId, newSequenceNumber).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_CHANGE_PROGRESS)
        );

        changingProgress.updateSequence(currentSequenceNumber);

        changedProgress.updateSequence(newSequenceNumber);

        return new ProgressResponseDto(changedProgress);
    }

    public void equalStatusName(Progress pr, ProgressCreateRequestDto requestDto){
        if (pr.getStatusName().equals(requestDto.getStatusName())) {
            throw new CustomException(ErrorType.DUPLICATE_PROGRESS_STATUS);
        }
    }


    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOARD)
        );
    }

    public Progress getProgress(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(
                () ->  new CustomException(ErrorType.NOT_FOUND_PROGRESS)
        );
    }

    public void validatedOwner(Board board, User user) {
        if (!board.getUser().getId().equals(user.getId())) {
            throw  new CustomException(ErrorType.UNAUTHORIZED_ACCESS);
        }
    }

}
