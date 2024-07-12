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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final BoardRepository boardRepository;
    private final ProgressRepository progressRepository;

    @Transactional
    public ProgressResponseDto addProgress(Long boardId, ProgressCreateRequestDto requestDto, User user) {

        Board board = existingBoard(boardId);

        validatedOwner(board, user);

        List<Progress> existingProgresses = progressRepository.findByBoardId(boardId);
        for (Progress pr : existingProgresses) {
            if (pr.getStatusName().equals(requestDto.getStatusName())) {
                throw new CustomException(ErrorType.DUPLICATE_PROGRESS_STATUS);
            }
        }

        Progress progress = new Progress(requestDto, board);

        Long countColumns = progressRepository.countByBoardId(boardId);

        progress.updateSequence(countColumns + 1);

        progressRepository.save(progress);

        return new ProgressResponseDto(progress);
    }

    public void deleteProgress(Long boardId, Long progressId, User user) {

        Board board = existingBoard(boardId);

        validatedOwner(board, user);

        Progress progress = existingProgress(progressId);

        progressRepository.delete(progress);

        List<Progress> progressList = progressRepository.findByBoardIdAndSequenceNumberGreaterThan(boardId, progress.getSequenceNumber());
        for (Progress pr : progressList) {
            pr.updateSequence(pr.getSequenceNumber() - 1);
        }

        progressRepository.saveAll(progressList);
    }

    @Transactional
    public ProgressResponseDto moveProgress(Long boardId, Long progressId, ProgressMoveRequestDto requestDto, User user) {

        Board board = existingBoard(boardId);

        validatedOwner(board, user);

        Progress changedProgress = existingProgress(progressId);

        Long newSequenceNumber = requestDto.getSequenceNumber();
        Long currentSequenceNumber = changedProgress.getSequenceNumber();

        Progress changingProgress = progressRepository.findByBoardIdAndSequenceNumber(boardId, newSequenceNumber).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_CHANGE_PROGRESS)
        );

        changingProgress.updateSequence(currentSequenceNumber);
        progressRepository.save(changingProgress);

        changedProgress.updateSequence(newSequenceNumber);
        progressRepository.save(changedProgress);

        return new ProgressResponseDto(changedProgress);
    }


    public Board existingBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOARD)
        );
    }

    public Progress existingProgress(Long progressId) {
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
