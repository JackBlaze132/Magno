package com.unibague.magno.domain.exception.security;

public class NotAllowedToDoThisActionException extends RuntimeException {
    public NotAllowedToDoThisActionException(String message) {
        super(message);
    }
}
