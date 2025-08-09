package com.unibague.magno.infrastructure.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.security.InvalidEmailException;
import com.unibague.magno.domain.exception.security.NullEmailException;
import com.unibague.magno.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;
    private final IUserServicePort userServicePort;
    private final GoogleIdTokenAuthenticationFilter googleIdTokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/oauth2/code/google", "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
                            String email = (String) user.getAttributes().get("email");

                            if (email != null && email.endsWith("@estudiantesunibague.edu.co")) {
                                response.sendRedirect("http://localhost:5173/student/home");
                            } else {
                                response.sendRedirect("http://localhost:5173/actor/home");
                            }
                        })
                        .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService()))
                )
                .addFilterBefore(googleIdTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            String email = (String) oAuth2User.getAttributes().get("email");
            String image = (String) oAuth2User.getAttributes().get("picture");

            User user = userServicePort.findByEmail(email);
            Map<String, Object> response = Map.of();

            System.out.println(">>> Atributos que se enviarán al frontend (OAuth):");
            try {
                new ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValue(System.out, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new DefaultOAuth2User(oAuth2User.getAuthorities(), response, "email");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
