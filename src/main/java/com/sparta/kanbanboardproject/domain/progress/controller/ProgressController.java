package com.sparta.kanbanboardproject.domain.progress.controller;

import com.sparta.kanbanboardproject.domain.progress.dto.ProgressCreateRequestDto;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressMoveRequestDto;
import com.sparta.kanbanboardproject.domain.progress.dto.ProgressResponseDto;
import com.sparta.kanbanboardproject.domain.progress.service.ProgressService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "ProgressController")
@RestController
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/boards/{board_id}/progresses")
    public ResponseEntity<ProgressResponseDto> addProgress(
            @PathVariable Long board_id,
            @Valid @RequestBody ProgressCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProgressResponseDto progressResponseDto = progressService.addProgress(board_id, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(progressResponseDto);
    }

    @DeleteMapping("/boards/{board_id}/progresses/{progress_id}")
    public ResponseEntity<String> deleteProgress(
            @PathVariable Long board_id,
            @PathVariable Long progress_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        progressService.deleteProgress(board_id, progress_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("컬럼이 삭제 되었습니다.");
    }

    @PutMapping("/boards/{board_id}/progresses/{progress_id}/sequence")
    public ResponseEntity<ProgressResponseDto> moveProgress(
            @PathVariable Long board_id,
            @PathVariable Long progress_id,
            @Valid @RequestBody ProgressMoveRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProgressResponseDto progressResponseDto = progressService.moveProgress(board_id,progress_id,requestDto,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(progressResponseDto);
    }
}
