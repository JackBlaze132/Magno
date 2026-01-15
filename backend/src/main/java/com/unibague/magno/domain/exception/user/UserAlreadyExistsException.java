package com.unibague.magno.domain.exception.user;

/**
 * Exception thrown when attempting to create a user that already exists in the system.
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException() {
    }
}
