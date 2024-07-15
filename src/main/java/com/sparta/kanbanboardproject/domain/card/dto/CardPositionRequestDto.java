package com.sparta.kanbanboardproject.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardPositionRequestDto {
    private Long newProgressId;

    @NotBlank(message = "카드의 위치를 변경할 숫자를 입력해 주세요.")
    private Long sequenceNum;
}