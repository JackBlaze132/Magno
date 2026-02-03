package com.unibague.magno.infrastructure.configuration.security;

import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.infrastructure.configuration.security.handler.CustomLogoutSuccessHandler;
import com.unibague.magno.infrastructure.configuration.security.handler.CustomOAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main Spring Security configuration class.
 * Configures OAuth2 login with Google, CORS, CSRF, authentication filters,
 * logout handling, and defines which endpoints require authentication.
 * This configuration is excluded from test profile.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;
    private final IUserServicePort userServicePort;
    private final GoogleIdTokenAuthenticationFilter googleIdTokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomOAuth2SuccessHandler customOAuth2SuccessHandler,
                                                   CustomLogoutSuccessHandler customLogoutSuccessHandler)throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/oauth2/code/google", "/auth/login",
                                "/auth/dev-token", "/auth/dev-users", "/health").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customOAuth2SuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                )
                .addFilterBefore(googleIdTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
