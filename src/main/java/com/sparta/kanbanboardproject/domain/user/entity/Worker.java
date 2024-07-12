package com.sparta.kanbanboardproject.domain.user.entity;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaborator_id")
    private Collaborator collaborator;

    public Worker(Card card, Collaborator collaborator) {
        this.card = card;
        this.collaborator = collaborator;
    }

    public User getUser() {
        if (collaborator != null) {
            return collaborator.getUser();
        }
        return null;
    }

}
