package com.sparta.kanbanboardproject.domain.progress.controller;

import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressResponseDto;
import com.sparta.kanbanboardproject.domain.progress.service.ProgressService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
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
    //String 말고 ResponseDto
    @PostMapping("/{board_id}/progresses")
    public ResponseEntity<ProgressResponseDto> addProgress(
            @PathVariable Long board_id,
            @Valid @RequestBody ProgressCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(progressService.addProgress(board_id, requestDto, userDetails.getUser()) + "컬럼이 생성 되었습니다.");
    }
}
