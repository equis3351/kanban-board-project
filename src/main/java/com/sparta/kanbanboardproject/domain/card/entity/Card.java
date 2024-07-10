package com.sparta.kanbanboardproject.domain.card.entity;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id")
    private Progress progress;

    @Builder
    public Card(String title, String content, LocalDateTime dueDate, Long sequence, Board board, Progress progress) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.sequence = sequence;
        this.board = board;
        this.progress = progress;
    }

}
