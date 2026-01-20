package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.configuration.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for development/testing authentication.
 * This controller is ONLY available in the "dev" profile and should NOT be used in production.
 * It provides endpoints to generate JWT tokens for testing purposes without going through OAuth2 flow.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Profile("dev")
public class DevAuthController {

    private final JwtService jwtService;
    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;

    /**
     * Generates a development JWT token for a user identified by email.
     * This endpoint is only available in the "dev" profile.
     *
     * @param email The email of the user to generate a token for
     * @return A JWT token that can be used in the Authorization header
     */
    @PostMapping(value = "/dev-token",headers = "API-Version=1")
    public ResponseEntity<Map<String, Object>> generateDevToken(@RequestParam String email) {
        try {
            User user = userServicePort.findByEmail(email);
            List<Role> roles = roleServicePort.findAllRolesByUserId(user.getId());

            if (roles.isEmpty()) {
                roles.add(roleServicePort.findByName(SeedbedRole.USUARIO_SIN_ROL));
            }

            List<String> roleNames = roles.stream()
                    .map(role -> role.getName().getAuthority())
                    .toList();

            String token = jwtService.generateToken(
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    roleNames
            );

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "type", "Bearer",
                    "userId", user.getId(),
                    "email", user.getEmail(),
                    "name", user.getFullName(),
                    "roles", roleNames,
                    "expiresIn", "24h",
                    "usage", "Add to requests as: Authorization: Bearer " + token.substring(0, 20) + "..."
            ));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "User not found",
                    "message", "No user exists with email: " + email
            ));
        }
    }

    /**
     * Lists all available users for token generation (dev only).
     * Useful for discovering which emails can be used to generate tokens.
     *
     * @return A list of user emails available for token generation
     */
    @GetMapping(value = "/dev-users", headers = "API-Version=1")
    public ResponseEntity<Map<String, Object>> listDevUsers() {
        List<User> users = userServicePort.findAll();
        
        List<Map<String, Object>> userList = users.stream()
                .map(user -> Map.<String, Object>of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "name", user.getFullName()
                ))
                .toList();

        return ResponseEntity.ok(Map.of(
                "message", "Available users for dev token generation",
                "users", userList,
                "usage", "POST /api/auth/dev-token?email=<email>"
        ));
    }
}

