package com.unibague.magno.domain.exception.externaluser;

/**
 * Exception thrown when attempting to perform an operation specific to external users on a user who is not external.
 */
public class UserIsNotExternalException extends RuntimeException{

    public UserIsNotExternalException(String message) {
        super(message);
    }

    public UserIsNotExternalException() {
    }
}
