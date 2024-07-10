package com.sparta.kanbanboardproject.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String boardName;
    private String boardIntro;
}
