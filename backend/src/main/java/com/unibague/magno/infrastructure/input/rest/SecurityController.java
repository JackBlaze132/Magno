package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.response.GoogleInfoResponse;
import com.unibague.magno.infrastructure.configuration.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for security-related operations in Magno.
 * Provides endpoints for retrieving information about the currently
 * authenticated user from the Google OAuth2 session.
 *
 * @see SecurityService
 */
@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    /**
     * Retrieves the current authenticated user's Google account information.
     *
     * @param authentication The Spring Security authentication object.
     * @return User information from the Google OAuth2 session.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(value = "/me", headers = "API-VERSION=1")
    public GoogleInfoResponse getCurrentUser(Authentication authentication) {
        return securityService.getInfoFromAuthenticatedUser(authentication);
    }

}
