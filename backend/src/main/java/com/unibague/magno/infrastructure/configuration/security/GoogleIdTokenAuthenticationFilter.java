package com.unibague.magno.infrastructure.configuration.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Security filter for authenticating requests using Google ID tokens or Magno development tokens.
 * Validates Bearer tokens in the Authorization header, verifies them with Google or validates
 * Magno JWT tokens (for development), and establishes the security context with user roles for API requests.
 */
@Component
@RequiredArgsConstructor
@Profile("!test")
public class GoogleIdTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            // First, try to validate as Magno development token
            if (tryAuthenticateWithMagnoToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // If not a Magno token, try Google ID token
            if (!tryAuthenticateWithGoogleToken(token, response)) {
                return; // Response already set to UNAUTHORIZED
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Attempts to authenticate using a Magno development JWT token.
     *
     * @param token The token to validate
     * @return true if authentication was successful, false if not a valid Magno token
     */
    private boolean tryAuthenticateWithMagnoToken(String token) {
        if (!jwtService.isMagnoDevToken(token)) {
            return false;
        }

        Claims claims = jwtService.validateToken(token);
        if (claims == null) {
            return false;
        }

        String email = claims.get("email", String.class);
        Long userId = claims.get("userId", Long.class);
        @SuppressWarnings("unchecked")
        List<String> roles = claims.get("roles", List.class);

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                .toList();

        // Create a DevTokenPrincipal for development tokens
        DevTokenPrincipal principal = new DevTokenPrincipal(userId, email, claims.get("name", String.class));
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return true;
    }

    /**
     * Attempts to authenticate using a Google ID token.
     *
     * @param token    The token to validate
     * @param response The HTTP response (used to set UNAUTHORIZED status on failure)
     * @return true if authentication was successful or token is not a Google token, false on error
     */
    private boolean tryAuthenticateWithGoogleToken(String token, HttpServletResponse response) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new JacksonFactory())
                .setAudience(List.of(googleClientId))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();

                User user = userServicePort.findByEmail(email);
                List<Role> roles = roleServicePort.findAllRolesByUserId(user.getId());

                if (roles.isEmpty()) {
                    roles.add(roleServicePort.findByName(SeedbedRole.USUARIO_SIN_ROL));
                }

                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName().getAuthority()))
                        .toList();

                // Create a custom principal that includes the application's user ID
                CustomPrincipalWithUserId principal = new CustomPrincipalWithUserId(payload, user.getId());
                Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

}

