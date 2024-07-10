package com.sparta.kanbanboardproject.domain.progress.controller;

import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import com.sparta.kanbanboardproject.domain.progress.service.ProgressService;
import com.sparta.kanbanboardproject.global.dto.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/{board_id}/progresses")
    public ResponseEntity<HttpResponseDto> addProgress(@PathVariable Long board_id,
                                                      @RequestBody ProgressCreateRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        progressService.addProgress(board_id, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("댓글이 작성이 완료되었습니다.")
                        .build()
        );
    }
}
