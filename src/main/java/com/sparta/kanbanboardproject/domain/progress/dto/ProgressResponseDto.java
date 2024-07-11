package com.sparta.kanbanboardproject.domain.progress.dto;


import com.sparta.kanbanboardproject.domain.progress.entity.Progress;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgressResponseDto {

    private String statusName;
    private Long sequenceNum;

    public ProgressResponseDto(Progress progress) {
        this.statusName = progress.getStatusName();
        this.sequenceNum = progress.getSequenceNumber();
    }
}
