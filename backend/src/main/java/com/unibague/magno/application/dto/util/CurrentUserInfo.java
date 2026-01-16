package com.unibague.magno.application.dto.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * DTO containing information about the currently authenticated user.
 * Extracted from the security context and used for authorization checks
 * and audit logging throughout the application.
 */
@Getter
@Builder
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurrentUserInfo {
    private final Long userId;
    private final List<String> roles;
    private final String email;
}
