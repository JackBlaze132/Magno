package com.unibague.magno.infrastructure.configuration.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwTokenService {

    private String secretKey = "clavesupersecretisima1234567890";
    private String issuer = "dev";

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;

    public String generateToken(String userEmail){
        User user = getUserFromEmail(userEmail);
        List<Role> roles = getUserRoles(user.getId());
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .toList();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(creationDate())
                .withExpiresAt(expirationDate())
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("name", user.getFullName())
                .withClaim("lastName", user.getUserCode())
                .withArrayClaim("roles", roleNames.toArray(new String[0]))
                .sign(algorithm);
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Bogota")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Bogota")).plusHours(4).toInstant();
    }

    public User getUserFromEmail(String email) {
        return userServicePort.findByEmail(email);
    }

    public List<Role> getUserRoles(Long id) {
        return roleServicePort.findAllRolesByUserId(id);
    }
}
