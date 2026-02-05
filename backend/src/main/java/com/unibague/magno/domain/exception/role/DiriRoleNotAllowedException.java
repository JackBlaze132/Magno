package com.unibague.magno.domain.exception.role;

/**
 * Exception thrown when attempting to create a profile with DIRI role
 * through unauthorized methods.
 * <p>
 * DIRI role profiles can only be created through specific system-level
 * operations such as the AdminRegistrationInitializer or the dedicated
 * DIRI user management endpoints.
 * </p>
 */
public class DiriRoleNotAllowedException extends RuntimeException {
    public DiriRoleNotAllowedException(String message) {
        super(message);
    }
}

