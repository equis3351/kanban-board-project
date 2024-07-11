package com.sparta.kanbanboardproject.domain.progress.service;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.board.repository.BoardRepository;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressResponseDto;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.progress.repository.ProgressRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
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

        if (!board.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("보드의 주인만 컬럼을 생성 할 수 있습니다.");
        }

        Progress progress = new Progress(requestDto, board);

        Long countColumns = progressRepository.countByBoardId(boardId);

        progress.setSequenceNumber(countColumns + 1);

        progressRepository.save(progress);

        return new ProgressResponseDto(progress);
    }

    public void deleteProgress(Long boardId, Long progressId, User user) {

        Board board = existingBoard(boardId);
        if (!board.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("보드의 주인만 컬럼을 삭제 할 수 있습니다.");
        }

        Progress progress = existingProgress(progressId);

        progressRepository.delete(progress);

        List<Progress> progressList = progressRepository.findByBoardIdAndSequenceNumberGreaterThan(boardId, progress.getSequenceNumber());
        for (Progress p : progressList) {
            p.setSequenceNumber(p.getSequenceNumber() - 1);
        }

        progressRepository.saveAll(progressList);
    }

    public Board existingBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("보드가 존재하지 않습니다.")
        );
    }

    public Progress existingProgress(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(
                () -> new IllegalArgumentException("컬럼이 존재하지 않습니다.")
        );
    }

    public ProgressResponseDto moveProgress(Long boardId, Long progressId, User user) {
        return null;
    }
}
