package com.unibague.magno.infrastructure.configuration.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomPrincipalWithUserId {
    private final GoogleIdToken.Payload payload;
    private final Long userId;
}
