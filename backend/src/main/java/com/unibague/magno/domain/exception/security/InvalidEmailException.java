package com.unibague.magno.domain.exception.security;

/**
 * Exception thrown when an invalid email address is provided.
 */
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
    public InvalidEmailException() {
    }
}
