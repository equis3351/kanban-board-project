package com.sparta.kanbanboardproject.domain.board.entity;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String boardName;

    @NotBlank
    @Column(nullable = false)
    private String boardIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Board(BoardRequestDto requestDto, User user) {
        this.boardName = requestDto.getBoardName();
        this.boardIntro = requestDto.getBoardIntro();
        this.user = user;
    }

    public void updateBoard(BoardRequestDto requestDto) {
        this.boardName = requestDto.getBoardName();
        this.boardIntro = requestDto.getBoardIntro();
    }

}
