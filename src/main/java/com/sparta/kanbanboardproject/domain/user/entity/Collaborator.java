package com.sparta.kanbanboardproject.domain.user.entity;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "collaborators")
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "collaborator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> workers;

    public Collaborator(Board board, User user) {
        this.board = board;
        this.user = user;
    }

}
