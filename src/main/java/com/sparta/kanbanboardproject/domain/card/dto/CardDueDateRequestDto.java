package com.sparta.kanbanboardproject.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Date;

@Getter
public class CardDueDateRequestDto {
    @NotBlank(message = "카드의 마감일을 입력해 주세요.")
    private Date dueDate;
}
