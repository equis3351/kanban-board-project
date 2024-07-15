package com.sparta.kanbanboardproject.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    @NotBlank(message = "보드의 이름을 입력해 주세요.")
    private String boardName;

    @NotBlank(message = "보드의 소개를 입력해 주세요.")
    private String boardIntro;
}
