package com.unibague.magno.infrastructure.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Configuration class to enable method-level security annotations.
 * Allows the use of @PreAuthorize and @PostAuthorize annotations
 * for role-based access control on service methods.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
}
