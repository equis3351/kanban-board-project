package com.sparta.kanbanboardproject.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardRequestDto<T> {
    @NotBlank(message = "필요한 데이터를 입력해 주세요.")
    private T data;
}
