package com.unibague.magno.domain.exception.user;

/**
 * Exception thrown when attempting to add a user to the DIRI group who is already a DIRI user.
 */
public class DiriUserAlreadyExistsException extends RuntimeException {
    public DiriUserAlreadyExistsException(String message) {
        super(message);
    }
}
