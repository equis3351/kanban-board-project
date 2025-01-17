package com.sparta.kanbanboardproject.domain.comment.service;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.card.repository.CardRepository;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentRequestDto;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentResponseDto;
import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import com.sparta.kanbanboardproject.domain.comment.repository.CommentRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.global.exception.CustomException;
import com.sparta.kanbanboardproject.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "CommentService")
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    // 댓글 작성
    public CommentResponseDto addComment(User user, Long cardId, CommentRequestDto commentRequestDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_CARD)
        );
        Comment comment = new Comment(commentRequestDto, card, user);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
    
    // 모든 댓글 조회
    public List<CommentResponseDto> getCardComment(Long cardId) {
        List<Comment> comment = commentRepository.findAllByCardId(cardId,Sort.by(Sort.Order.desc("createdAt")));
        return comment.stream().map(CommentResponseDto::new).toList();
    }
}
