package com.sparta.kanbanboardproject.domain.card.dto;

import com.sparta.kanbanboardproject.domain.user.entity.Worker;

import java.time.LocalDateTime;

public class CardUpdateRequestDto {
    private String content;
    private LocalDateTime dueDate;
    private Worker worker;
}
