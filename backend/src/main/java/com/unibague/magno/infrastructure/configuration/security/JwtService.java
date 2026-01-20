package com.unibague.magno.infrastructure.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service for generating and validating JWT tokens for development/testing purposes.
 * This service creates tokens that can be used instead of Google ID tokens
 * to facilitate frontend testing without going through OAuth2 flow.
 */
@Service
public class JwtService {

    @Value("${magno.jwt.secret}")
    private String jwtSecret;

    @Value("${magno.jwt.expiration-hours}")
    private int expirationHours;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the given user information.
     *
     * @param userId    The application's internal user ID
     * @param email     The user's email
     * @param name      The user's name
     * @param roles     List of role names
     * @return A signed JWT token string
     */
    public String generateToken(Long userId, String email, String name, List<String> roles) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + (long) expirationHours * 60 * 60 * 1000);

        return Jwts.builder()
                .subject(email)
                .claims(Map.of(
                        "userId", userId,
                        "email", email,
                        "name", name,
                        "roles", roles,
                        "type", "magno-dev-token"
                ))
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validates a JWT token and returns its claims.
     *
     * @param token The JWT token to validate
     * @return The claims contained in the token, or null if invalid
     */
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a token is a Magno development token (not a Google token).
     *
     * @param token The token to check
     * @return true if it's a Magno dev token, false otherwise
     */
    public boolean isMagnoDevToken(String token) {
        Claims claims = validateToken(token);
        return claims != null && "magno-dev-token".equals(claims.get("type"));
    }
}

