package com.sparta.kanbanboardproject.domain.card.entity;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import com.sparta.kanbanboardproject.domain.user.entity.Worker;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id")
    private Progress progress;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> workers;

    @Builder
    public Card(String title, String content, Date dueDate, Long sequence, Board board, Progress progress) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.sequence = sequence;
        this.board = board;
        this.progress = progress;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void updateWorker(Card card, Collaborator collaborator) {
        Worker worker = new Worker(card, collaborator);
        workers.add(worker);
    }

    public void updateProgress(Progress progress) {
        this.progress = progress;
    }
}
