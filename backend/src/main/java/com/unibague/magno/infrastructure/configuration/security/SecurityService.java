package com.unibague.magno.infrastructure.configuration.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.unibague.magno.application.dto.response.GoogleInfoResponse;
import com.unibague.magno.domain.exception.security.UnsupportedPrincipalException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public GoogleInfoResponse getInfoFromAuthenticatedUser(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOidcUser oidcUser) {
            return GoogleInfoResponse.builder()
                    .name(oidcUser.getAttribute("name"))
                    .email(oidcUser.getAttribute("email"))
                    .picture(oidcUser.getAttribute("picture"))
                    .build();
        }

        if (principal instanceof GoogleIdToken.Payload payload) {
            return GoogleInfoResponse.builder()
                    .name((String) payload.get("name"))
                    .email(payload.getEmail())
                    .picture((String) payload.get("picture"))
                    .build();
        }

        throw new UnsupportedPrincipalException();
    }
}

