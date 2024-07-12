package com.sparta.kanbanboardproject.domain.progress.dto;

import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import lombok.Getter;

@Getter
public class ProgressResponseDto {

    private final String statusName;
    private final Long sequenceNum;

    public ProgressResponseDto(Progress progress) {
        this.statusName = progress.getStatusName();
        this.sequenceNum = progress.getSequenceNumber();
    }
}
