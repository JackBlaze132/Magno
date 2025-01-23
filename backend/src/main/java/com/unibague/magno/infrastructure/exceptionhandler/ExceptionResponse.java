package com.unibague.magno.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {

    ACADEMIC_PERIOD_NOT_FOUND("ERR_ACADEMIC_PERIOD_001", "Academic period not found."),
    GENERIC_ERROR("ERR_GENERIC_001", "An unexpected error occurred.");;

    private final String code;
    private final String message;

    ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
