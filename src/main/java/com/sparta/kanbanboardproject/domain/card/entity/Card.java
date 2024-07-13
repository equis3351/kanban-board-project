package com.sparta.kanbanboardproject.domain.card.entity;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import com.sparta.kanbanboardproject.domain.user.entity.Collaborator;
import com.sparta.kanbanboardproject.domain.user.entity.User;
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

    @Column(name = "sequence", nullable = false)
    private Long sequenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id")
    private Progress progress;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Worker> workerList;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @Builder
    public Card(String title, String content, Date dueDate, Progress progress) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
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

    public void addWorker(Card card, User user) {
        Worker worker = new Worker(card, user);
        workerList.add(worker);
    }

    public void updateProgress(Progress progress) {
        this.progress = progress;
    }

    public void updateSequence(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void decreaseSequence(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber - 1;
    }

}
