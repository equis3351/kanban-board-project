package com.sparta.kanbanboardproject.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final String result;
    private final ErrorType errorType;

    public CustomException(ErrorType errorType) {
        this.result = "ERROR";
        this.errorType = errorType;
    }
}
