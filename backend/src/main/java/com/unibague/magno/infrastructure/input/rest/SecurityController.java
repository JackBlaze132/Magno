package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.response.GoogleInfoResponse;
import com.unibague.magno.infrastructure.configuration.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    // No role restriction for this endpoint to allow any authenticated user to retrieve their own information
    @GetMapping(value = "/me", headers = "API-VERSION=1")
    public GoogleInfoResponse getCurrentUser(Authentication authentication) {
        return securityService.getInfoFromAuthenticatedUser(authentication);
    }

}
