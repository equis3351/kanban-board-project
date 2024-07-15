package com.sparta.kanbanboardproject.domain.board.entity;

import com.sparta.kanbanboardproject.domain.board.dto.BoardRequestDto;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private String boardIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Progress> progressList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collaborator> collaboratorList;

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
