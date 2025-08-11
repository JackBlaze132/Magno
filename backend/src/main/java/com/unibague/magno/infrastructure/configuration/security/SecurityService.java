package com.unibague.magno.infrastructure.configuration.security;

import com.unibague.magno.application.dto.response.GoogleInfoResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public GoogleInfoResponse getInfoFromAuthenticatedUser(Authentication authentication) {
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        return GoogleInfoResponse.builder()
                .name(oidcUser.getAttribute("name"))
                .email(oidcUser.getAttribute("email"))
                .picture(oidcUser.getAttribute("picture"))
                .build();
    }
}
