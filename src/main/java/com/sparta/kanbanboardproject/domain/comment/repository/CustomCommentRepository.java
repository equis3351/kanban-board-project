package com.sparta.kanbanboardproject.domain.comment.repository;

import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> findAllByCardId(Long cardId, Sort createdAt);
}
