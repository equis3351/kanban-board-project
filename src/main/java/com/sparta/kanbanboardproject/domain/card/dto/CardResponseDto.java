package com.sparta.kanbanboardproject.domain.card.dto;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class CardResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Date dueDate;
    private final Long sequence;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.dueDate = card.getDueDate();
        this.sequence = card.getSequence();
    }

}
