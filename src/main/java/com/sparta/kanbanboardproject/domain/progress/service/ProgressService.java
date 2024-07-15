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

    // 컬럼 추가
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

    // 컬럼 삭제
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

    // 컬럼 이동
    @Transactional
    public ProgressResponseDto moveProgress(Long boardId, Long progressId, ProgressMoveRequestDto requestDto, User user) {
        Board board = getBoard(boardId);
        validatedOwner(board, user);

        Progress movedProgress = getProgress(progressId);
        Long newSequenceNumber = requestDto.getSequenceNumber();

        List<Progress> progresses = progressRepository.findByBoardIdOrderBySequenceNumber(boardId);
        updateProgressSequences(progresses, movedProgress, newSequenceNumber);

        return new ProgressResponseDto(movedProgress);
    }

    // 컬럼 상태 중복 확인
    public void equalStatusName(Progress pr, ProgressCreateRequestDto requestDto){
        if (pr.getStatusName().equals(requestDto.getStatusName())) {
            throw new CustomException(ErrorType.DUPLICATE_PROGRESS_STATUS);
        }
    }

    // 보드 가져오기
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOARD)
        );
    }

    // 컬럼 가져오기
    public Progress getProgress(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(
                () ->  new CustomException(ErrorType.NOT_FOUND_PROGRESS)
        );
    }

    // 보드 사용자 검증
    public void validatedOwner(Board board, User user) {
        if (!board.getUser().getId().equals(user.getId())) {
            throw  new CustomException(ErrorType.UNAUTHORIZED_ACCESS);
        }
    }

    // 컬럼 순번 업데이트
    private void updateProgressSequences(List<Progress> progresses, Progress movedProgress, Long newSequenceNum) {
        Long currentSequenceNumber = movedProgress.getSequenceNumber();

        if (newSequenceNum < 0 || newSequenceNum > progresses.size()) {
            throw new CustomException(ErrorType.INVALID_SEQUENCE_NUMBER);
        }

        if (newSequenceNum.equals(currentSequenceNumber)) {
            return;
        }

        if (newSequenceNum < currentSequenceNumber) {
            for (Progress progress : progresses) {
                if (progress.getSequenceNumber() >= newSequenceNum && progress.getSequenceNumber() < currentSequenceNumber) {
                    progress.increaseSequence(progress.getSequenceNumber());
                }
            }
        } else {
            for (Progress progress : progresses) {
                if (progress.getSequenceNumber() > currentSequenceNumber && progress.getSequenceNumber() <= newSequenceNum) {
                    progress.decreaseSequence(progress.getSequenceNumber());
                }
            }
        }

        movedProgress.updateSequence(newSequenceNum);
    }

}
