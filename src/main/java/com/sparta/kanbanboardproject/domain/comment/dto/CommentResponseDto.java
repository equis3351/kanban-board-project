package com.sparta.kanbanboardproject.domain.comment.dto;

import com.sparta.kanbanboardproject.domain.comment.entity.Comment;

public class CommentResponseDto {
    private Long id;
    private String comment;

    public CommentResponseDto(Comment comment){
        this.id=comment.getId();
        this.comment=comment.getComment();
    }
}
