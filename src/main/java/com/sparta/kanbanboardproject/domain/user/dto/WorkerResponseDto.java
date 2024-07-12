package com.sparta.kanbanboardproject.domain.user.dto;

import com.sparta.kanbanboardproject.domain.user.entity.Worker;
import lombok.Getter;

@Getter
public class WorkerResponseDto {
    private final Long id;
    private final Long cardId;
    private final Long userId;

    public WorkerResponseDto(Worker worker) {
        this.id = worker.getId();
        this.cardId = worker.getCard().getId();
        this.userId = worker.getUser().getId();
    }
}
