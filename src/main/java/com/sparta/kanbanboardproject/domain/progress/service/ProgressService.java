package com.sparta.kanbanboardproject.domain.progress.service;

import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.progress.repository.ProgressRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgressService {

    BoardRepository boardRepository;
    ProgressRepository progressRepository;

    @Transactional
    public void addProgress(Long boardId, ProgressCreateRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("보드가 존재하지 않습니다.")
        );
        Progress progress = new Progress(requestDto, board);
    }
}
