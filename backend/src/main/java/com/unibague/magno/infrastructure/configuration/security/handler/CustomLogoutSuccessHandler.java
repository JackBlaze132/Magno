package com.unibague.magno.infrastructure.configuration.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for successful logout operations.
 * Clears the session cookie and redirects the user to the login page.
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Value("${app.frontend.login-redirect}")
    private String loginRedirectUrl;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        ResponseCookie deleteCookie = ResponseCookie.from("MAGNO_SESSION", "")
                .httpOnly(true)
                .secure(request.isSecure())
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", deleteCookie.toString());
        response.sendRedirect(loginRedirectUrl);
    }
}

