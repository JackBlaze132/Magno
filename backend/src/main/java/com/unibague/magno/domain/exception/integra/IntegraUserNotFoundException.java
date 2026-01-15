package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when a user cannot be found in the Integra external system.
 */
public class IntegraUserNotFoundException extends RuntimeException {

    public IntegraUserNotFoundException(String message) {
        super(message);
    }

    public IntegraUserNotFoundException() {

    }
}
