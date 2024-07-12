package com.sparta.kanbanboardproject.domain.comment.service;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.repository.CardRepository;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentRequestDto;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentResponseDto;
import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import com.sparta.kanbanboardproject.domain.comment.repository.CommentRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;


    public CommentResponseDto addComment(User user, Long cardId, CommentRequestDto commentRequestDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("not found")
        );
        Comment comment = new Comment(commentRequestDto, card, user);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getAllComment() {
        List<Comment> comment = commentRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));

        return comment.stream().map(CommentResponseDto::new).toList();
    }
}
