package com.sparta.kanbanboardproject.domain.progress.entity;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "progresses")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long id;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column(name = "sequence", nullable = false)
    private Long sequenceNumber;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "progress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cardList;

    public Progress(ProgressCreateRequestDto requestDto, Board board) {
        this.statusName = requestDto.getStatusName();
        this.board = board;
    }

    public void updateSequence(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
