package com.sparta.kanbanboardproject.domain.comment.controller;


import com.sparta.kanbanboardproject.domain.comment.dto.CommentRequestDto;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentResponseDto;
import com.sparta.kanbanboardproject.domain.comment.service.CommentService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "CommentController")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long cardId,
            @Valid @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.addComment(userDetails.getUser(), cardId, requestDto));
    }

    @GetMapping("/cards/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComment() {
        return ResponseEntity.status(HttpStatus.OK).
                body(commentService.getAllComment());
    }

}
