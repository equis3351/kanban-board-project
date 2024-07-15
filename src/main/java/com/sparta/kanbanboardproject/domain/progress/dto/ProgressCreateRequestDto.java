package com.sparta.kanbanboardproject.domain.progress.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProgressCreateRequestDto {
    @NotBlank(message = "컬럼의 상태를 입력해 주세요.")
    private String statusName;
}
