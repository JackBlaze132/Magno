package com.unibague.magno.infrastructure.configuration.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.unibague.magno.application.dto.response.GoogleInfoResponse;
import com.unibague.magno.domain.exception.security.UnsupportedPrincipalException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

/**
 * Service for extracting authenticated user information from the security context.
 * Handles OAuth2 web-based authentication, Bearer token authentication (Google),
 * and development JWT token authentication, returning a unified response with user details.
 */
@Service
public class SecurityService {

    public GoogleInfoResponse getInfoFromAuthenticatedUser(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomOidcUserWithUserId customUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) customUser.getDelegate();
            return GoogleInfoResponse.builder()
                    .userId(customUser.getUserId())
                    .name(oidcUser.getAttribute("name"))
                    .email(oidcUser.getAttribute("email"))
                    .picture(oidcUser.getAttribute("picture"))
                    .build();
        }

        if (principal instanceof CustomPrincipalWithUserId customPrincipal) {
            GoogleIdToken.Payload payload = customPrincipal.getPayload();
            return GoogleInfoResponse.builder()
                    .userId(customPrincipal.getUserId())
                    .name((String) payload.get("name"))
                    .email(payload.getEmail())
                    .picture((String) payload.get("picture"))
                    .build();
        }

        // Handle development token principals
        if (principal instanceof DevTokenPrincipal devPrincipal) {
            return GoogleInfoResponse.builder()
                    .userId(devPrincipal.getUserId())
                    .name(devPrincipal.getName())
                    .email(devPrincipal.getEmail())
                    .picture(null) // Development tokens don't include pictures
                    .build();
        }

        throw new UnsupportedPrincipalException();
    }
}

