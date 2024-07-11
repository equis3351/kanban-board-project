package com.sparta.kanbanboardproject.domain.board.entity;


import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String boardName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String boardIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    public Board(BoardRequestDto requestDtp, User user) {
        this.boardName = requestDtp.getBoardName();
        this.boardIntro = requestDtp.getBoardIntro();
        this.user = user;
    }

    public void updateBoard(BoardRequestDto requestDtp) {
        this.boardName = requestDtp.getBoardName();
        this.boardIntro = requestDtp.getBoardIntro();
    }

}
