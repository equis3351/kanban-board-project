package com.sparta.kanbanboardproject.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글의 내용을 입력해 주세요.")
    private String comment;
}
