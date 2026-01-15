package com.unibague.magno.domain.exception.role;

/**
 * Exception thrown when a role cannot be found in the system.
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException() {
    }
}
