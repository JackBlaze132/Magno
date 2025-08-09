package com.unibague.magno.infrastructure.configuration.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleIdTokenAuthenticationFilter extends OncePerRequestFilter {

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("Header: " + headerName + " = " + request.getHeader(headerName));
        }



        System.out.println("Hola desde do filter");
        System.out.println("token: " + recoveryToken(request));
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new JacksonFactory())
                    .setAudience(List.of("177640253484-00hna9dcguhp90lq86tnr7ng2umnclki.apps.googleusercontent.com"))
                    .build();

            try {
                GoogleIdToken idToken = verifier.verify(token);
                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    String email = payload.getEmail();

                    User user = userServicePort.findByEmail(email);
                    List<Role> roles = roleServicePort.findAllRolesByUserId(user.getId());

                    List<GrantedAuthority> authorities = roles.stream()
                            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                            .toList();

                    Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Token verificado para: " + email);
                    System.out.println("Roles: " + authorities);
                    System.out.println("Autenticado: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("authorization"); // <-- en minúsculas
        System.out.println("Authorization header: " + bearerToken);
        if (bearerToken != null && bearerToken.toLowerCase().startsWith("bearer ")) {
            return bearerToken.substring(7); // evitar replace
        }
        return null;
    }

}

