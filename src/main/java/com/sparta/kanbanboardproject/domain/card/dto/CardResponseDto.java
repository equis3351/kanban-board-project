package com.sparta.kanbanboardproject.domain.card.dto;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.user.dto.WorkerResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Date dueDate;
    private final Long sequenceNumber;
    private final List<WorkerResponseDto> workerList;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.dueDate = card.getDueDate();
        this.sequenceNumber = card.getSequenceNumber();
        this.workerList = card.getWorkerList() != null
            ? card.getWorkerList().stream().map(WorkerResponseDto::new).collect(Collectors.toList())
            : new ArrayList<>();
    }

}
