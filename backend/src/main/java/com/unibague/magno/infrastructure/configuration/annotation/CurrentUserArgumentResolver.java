package com.unibague.magno.infrastructure.configuration.annotation;

import com.unibague.magno.application.dto.util.CurrentUserInfo;
import com.unibague.magno.domain.exception.security.UnsupportedPrincipalException;
import com.unibague.magno.infrastructure.configuration.security.CustomOidcUserWithUserId;
import com.unibague.magno.infrastructure.configuration.security.CustomPrincipalWithUserId;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(CurrentUserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        Long userId = extractUserId(principal);
        String email = extractEmail(principal);
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return CurrentUserInfo.builder()
                .userId(userId)
                .roles(roles)
                .email(email)
                .build();
    }

    private Long extractUserId(Object principal) {
        if (principal instanceof CustomOidcUserWithUserId customUser) {
            return customUser.getUserId();
        }
        if (principal instanceof CustomPrincipalWithUserId customPrincipal) {
            return customPrincipal.getUserId();
        }
        throw new UnsupportedPrincipalException();
    }

    private String extractEmail(Object principal) {
        if (principal instanceof CustomOidcUserWithUserId customUser) {
            return customUser.getEmail();
        }
        if (principal instanceof CustomPrincipalWithUserId customPrincipal) {
            return customPrincipal.getPayload().getEmail();
        }
        throw new UnsupportedPrincipalException();
    }
}