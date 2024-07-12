package com.sparta.kanbanboardproject.domain.comment.dto;

import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String comment;

    public CommentResponseDto(Comment comment) {
        this.id=comment.getId();
        this.comment=comment.getComment();
    }
}
