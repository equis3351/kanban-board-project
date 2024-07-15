package com.sparta.kanbanboardproject.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardContentRequestDto {
    @NotBlank(message = "카드의 내용을 입력해 주세요.")
    private String content;
}
