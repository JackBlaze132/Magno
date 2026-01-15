package com.unibague.magno.domain.exception.security;

/**
 * Exception thrown when a null email address is encountered where a valid email is required.
 */
public class NullEmailException extends RuntimeException {
    public NullEmailException(String message) {
        super(message);
    }
    public NullEmailException() {
    }
}
