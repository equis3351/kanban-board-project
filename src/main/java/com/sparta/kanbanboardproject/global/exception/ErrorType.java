package com.sparta.kanbanboardproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // LOGIN
    INVALID_ACCOUNT_ID(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // PROGRESS
    DUPLICATE_PROGRESS_STATUS(HttpStatus.LOCKED, "상태가 같은 컬럼을 또 생성할 수 없습니다."),
    NOT_FOUND_CHANGE_PROGRESS(HttpStatus.NOT_FOUND, "바꾸려는 컬럼을 찾을 수 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "보드가 존재하지 않습니다."),
    NOT_FOUND_PROGRESS(HttpStatus.NOT_FOUND, "컬럼이 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "보드의 권한에 맞지 않는 접근을 요청할 수 없습니다."),

    // CARD
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다."),
    NOT_FOUND_CHANGE_CARD(HttpStatus.NOT_FOUND, "바꾸려는 카드를 찾을 수 없습니다."),
    NOT_FOUND_COLLABORATOR(HttpStatus.NOT_FOUND, "협력자가 아닙니다."),
    DUPLICATE_CARD_WORKER(HttpStatus.LOCKED, "이미 존재하는 작업자입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
