package com.unibague.magno.infrastructure.configuration.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Custom principal wrapper for token-based (Bearer) authentication.
 * Contains the Google ID token payload along with the application's internal user ID.
 * Used when authenticating via Authorization header with Google ID tokens.
 */
@Getter
@AllArgsConstructor
public class CustomPrincipalWithUserId {
    private final GoogleIdToken.Payload payload;
    private final Long userId;
}
