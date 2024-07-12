package com.sparta.kanbanboardproject.domain.board.dto;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private final Long id;
    private final String boardName;
    private final String boardIntro;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.boardName = board.getBoardName();
        this.boardIntro = board.getBoardIntro();
    }

}
