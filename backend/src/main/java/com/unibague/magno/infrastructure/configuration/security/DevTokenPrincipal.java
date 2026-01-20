package com.unibague.magno.infrastructure.configuration.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Principal class for development JWT token authentication.
 * Contains the user information extracted from Magno development tokens,
 * allowing the application to identify authenticated users during testing.
 */
@Getter
@RequiredArgsConstructor
public class DevTokenPrincipal {

    private final Long userId;
    private final String email;
    private final String name;

    @Override
    public String toString() {
        return "DevTokenPrincipal{userId=" + userId + ", email='" + email + "'}";
    }
}

