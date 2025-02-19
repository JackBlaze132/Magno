package com.unibague.magno.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {

    ACADEMIC_PERIOD_NOT_FOUND("ERR_ACADEMIC_PERIOD_001", "Academic period not found."),
    ROLE_NOT_FOUND("ERR_ROLE_001", "Role not found."),
    DEPENDENCY_NOT_FOUND("ERR_DEPENDENCY_001", "Dependency not found."),
    ACADEMIC_PROGRAM_NOT_FOUND("ERR_ACADEMIC_PROGRAM_001", "Academic program not found."),
    USER_NOT_FOUND("ERR_USER_001", "User not found."),
    INVESTIGATION_GROUP_NOT_FOUND("ERR_INVESTIGATION_GROUP_001", "Investigation group not found."),
    INVESTIGATION_GROUP_PROFILE_NOT_FOUND("ERR_INVESTIGATION_GROUP_PROFILE_001",
            "Investigation group profile not found."),
    RESEARCH_SEEDBED_NOT_FOUND("ERR_RESEARCH_SEEDBED_001", "Research seedbed not found."),
    RESEARCH_SEEDBED_PROFILE_NOT_FOUND("ERR_RESEARCH_SEEDBED_PROFILE_001",
            "Research seedbed profile not found."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_NOT_FOUND("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_001",
            "Research seedbed student profile not found."),
    FUNCTIONARY_PROFILE_NOT_FOUND("ERR_FUNCTIONARY_PROFILE_001", "Functionary profile not found."),
    STUDENT_PROFILE_NOT_FOUND("ERR_STUDENT_PROFILE_001", "Student profile not found."),
    EXTERNAL_USER_PROFILE_NOT_FOUND("ERR_EXTERNAL_USER_PROFILE_001", "External user profile not found."),
    ENUM_BAD_REQUEST("ERR_ENUM_001", "Invalid enum value."),
    INTEGRA_API_ERROR("ERR_INTEGRA_API_001", "An error occurred while trying to connect to the " +
            "Integra API."),
    UPLOAD_EXCEL_ERROR("ERR_UPLOAD_EXCEL_001", "An error occurred while trying to upload the Excel file."),
    GENERIC_ERROR("ERR_GENERIC_001", "An unexpected error occurred.");

    private final String code;
    private final String message;

    ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
