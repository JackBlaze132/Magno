package com.unibague.magno.domain.exception.security;

/**
 * Exception thrown when a user attempts to perform an action they are not authorized to execute.
 */
public class NotAllowedToDoThisActionException extends RuntimeException {
    public NotAllowedToDoThisActionException(String message) {
        super(message);
    }
}
