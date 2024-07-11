package com.sparta.kanbanboardproject.domain.card.dto;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.user.entity.Worker;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class CardResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Date dueDate;
    private final Long sequenceNumber;
    private final List<Worker> workers;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.dueDate = card.getDueDate();
        this.sequenceNumber = card.getSequenceNumber();
        this.workers = card.getWorkerList();
    }

}
