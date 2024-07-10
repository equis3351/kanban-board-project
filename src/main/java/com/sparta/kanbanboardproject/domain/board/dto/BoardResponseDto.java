package com.sparta.kanbanboardproject.domain.board.dto;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String boardName;
    private String boardIntro;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.boardName = getBoardName();
        this.boardIntro = getBoardIntro();
    }

}
