package com.unibague.magno.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
// DTO that holds information about the currently authenticated user.
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurrentUserInfo {
    private final Long userId;
    private final List<String> roles;
    private final String email;
}
