package com.sparta.kanbanboardproject.domain.card.dto;

import lombok.Getter;

@Getter
public class CardRequestDto<T> {
    private T data;
}
