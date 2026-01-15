package com.unibague.magno.domain.exception.user;

/**
 * Exception thrown when a user cannot be found in the system.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException () {

    }
}
