package com.unibague.magno.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {

    ACADEMIC_PERIOD_NOT_FOUND("ERR_ACADEMIC_PERIOD_001", "Academic period not found."),
    ROLE_NOT_FOUND("ERR_ROLE_001", "Role not found."),
    DEPENDENCY_NOT_FOUND("ERR_DEPENDENCY_001", "Dependency not found."),
    GENERIC_ERROR("ERR_GENERIC_001", "An unexpected error occurred.");;

    private final String code;
    private final String message;

    ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
