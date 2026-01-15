package com.unibague.magno.domain.exception.user;

/**
 * Exception thrown when a DIRI user cannot be found in the system.
 */
public class DiriUserNotFoundException extends RuntimeException {
    public DiriUserNotFoundException(String message) {
        super(message);
    }
}
