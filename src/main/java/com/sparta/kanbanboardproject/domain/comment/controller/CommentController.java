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
            @RequestBody @Valid CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.addComment(userDetails.getUser(), cardId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping("/cards/{cardId}/comments")
    //한 카드에 대한 댓글만 조회
    public ResponseEntity<List<CommentResponseDto>> getCardComment(@PathVariable Long cardId) {
        List<CommentResponseDto> reponseDtoList = commentService.getCardComment(cardId);
        return ResponseEntity.status(HttpStatus.OK).body(reponseDtoList);
    }

}
