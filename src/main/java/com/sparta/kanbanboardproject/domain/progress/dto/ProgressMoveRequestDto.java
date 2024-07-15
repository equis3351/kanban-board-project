package com.sparta.kanbanboardproject.domain.progress.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProgressMoveRequestDto {
    @NotBlank(message = "컬럼의 위치를 변경할 숫자를 입력해 주세요.")
    private Long sequenceNumber;
}
