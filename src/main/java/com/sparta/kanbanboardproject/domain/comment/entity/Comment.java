package com.sparta.kanbanboardproject.domain.comment.entity;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentRequestDto;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto commentRequestDto, Card card, User user) {
        this.comment = commentRequestDto.getComment();
        this.card = card;
        this.user = user;
    }
}
