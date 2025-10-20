package com.unibague.magno.application.dto;

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
public class CurrentUserInfo {
    private final Long userId;
    private final List<String> roles;
    private final String email;
}
