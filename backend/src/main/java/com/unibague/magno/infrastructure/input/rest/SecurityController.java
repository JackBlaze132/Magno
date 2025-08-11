package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.response.GoogleInfoResponse;
import com.unibague.magno.infrastructure.configuration.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/me")
    public GoogleInfoResponse getCurrentUser(Authentication authentication) {
        return securityService.getInfoFromAuthenticatedUser(authentication);
    }

}
