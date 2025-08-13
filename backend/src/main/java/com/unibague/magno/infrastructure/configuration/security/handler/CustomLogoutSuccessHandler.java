package com.unibague.magno.infrastructure.configuration.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

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
        response.sendRedirect("http://localhost:5173/login");
    }
}

