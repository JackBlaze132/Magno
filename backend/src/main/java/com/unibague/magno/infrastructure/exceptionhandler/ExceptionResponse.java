package com.unibague.magno.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {

    ACADEMIC_PERIOD_NOT_FOUND("ERR_ACADEMIC_PERIOD_001", "Academic period not found."),
    ROLE_NOT_FOUND("ERR_ROLE_001", "Role not found."),
    DEPENDENCY_NOT_FOUND("ERR_DEPENDENCY_001", "Dependency not found."),
    ACADEMIC_PROGRAM_NOT_FOUND("ERR_ACADEMIC_PROGRAM_001", "Academic program not found."),
    USER_NOT_FOUND("ERR_USER_001", "User not found."),
    USER_ALREADY_EXISTS("ERR_USER_002", "User already exists."),
    USER_IS_NOT_EXTERNAL("ERR_USER_003", "User is not external."),
    INVESTIGATION_GROUP_NOT_FOUND("ERR_INVESTIGATION_GROUP_001", "Investigation group not found."),
    INVESTIGATION_GROUP_PROFILE_NOT_FOUND("ERR_INVESTIGATION_GROUP_PROFILE_001",
            "Investigation group profile not found."),
    INVESTIGATION_GROUP_PROFILE_DUPLICATED_IN_SAME_ACADEMIC_PERIOD("ERR_INVESTIGATION_GROUP_PROFILE_002",
            "An investigation group profile for the given investigation group in the specified academic period already exists."),
    RESEARCH_SEEDBED_NOT_FOUND("ERR_RESEARCH_SEEDBED_001", "Research seedbed not found."),
    RESEARCH_SEEDBED_PROFILE_NOT_FOUND("ERR_RESEARCH_SEEDBED_PROFILE_001",
            "Research seedbed profile not found."),
    RESEARCH_SEEDBED_PROFILE_SAME_COORDINATOR_AND_TUTOR("ERR_RESEARCH_SEEDBED_PROFILE_002",
            "The coordinator and tutor cannot be the same person."),
    RESEARCH_SEEDBED_PROFILE_ALREADY_EXISTS_IN_INVESTIGATION_GROUP("ERR_RESEARCH_SEEDBED_PROFILE_003",
            "A research seedbed profile for the given research seedbed in the specified investigation group profile already exists."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_NOT_FOUND("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_001",
            "Research seedbed student profile not found."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_ALREADY_EXISTS("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_002",
            "The student profile is already associated with the research seedbed profile."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_LEADER_ALREADY_EXISTS("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_003",
            "The research seedbed profile already has a leader assigned, unassign the current leader before assigning a new one."),
    FUNCTIONARY_PROFILE_NOT_FOUND("ERR_FUNCTIONARY_PROFILE_001", "Functionary profile not found."),
    FUNCTIONARY_PROFILE_ALREADY_EXISTS("ERR_FUNCTIONARY_PROFILE_002", "Functionary profile already exists."),
    STUDENT_PROFILE_NOT_FOUND("ERR_STUDENT_PROFILE_001", "Student profile not found."),
    STUDENT_PROFILE_ALREADY_EXISTS("ERR_STUDENT_PROFILE_002", "Student profile already exists."),
    EXTERNAL_USER_PROFILE_NOT_FOUND("ERR_EXTERNAL_USER_PROFILE_001", "External user profile not found."),
    ENUM_BAD_REQUEST("ERR_ENUM_001", "Invalid enum value."),
    INVALID_SEEDBED_ROLE("ERR_ENUM_002", "Invalid SeedbedRole value."),
    INTEGRA_API_ERROR("ERR_INTEGRA_API_001", "An error occurred while trying to connect to the " +
            "Integra API."),
    UPLOAD_EXCEL_ERROR("ERR_UPLOAD_EXCEL_001", "An error occurred while trying to upload the Excel file."),
    NULL_EMAIL("ERR_NULL_EMAIL_001", "Email cannot be null."),
    INVALID_EMAIL("ERR_INVALID_EMAIL_001", "The provided email is invalid, it must be a valid Unibague email."),
    NULL_INTEGRA_RESPONSE("ERR_NULL_INTEGRA_RESPONSE_001", "Integra response was null."),
    UNSUPPORTED_PRINCIPAL("ERR_UNSUPPORTED_PRINCIPAL_001",  "Unsupported principal (authentication) type."),
    FORBIDDEN_REQUEST("ERR_FORBIDDEN_001", "You do not have permission to access this resource."),
    SQL_EXCEPTION("ERR_SQL_001", "A database error occurred."),
    GENERIC_ERROR("ERR_GENERIC_001", "An unexpected error occurred.");

    private final String code;
    private final String message;

    ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
