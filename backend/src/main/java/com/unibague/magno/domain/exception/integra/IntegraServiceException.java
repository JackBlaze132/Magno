package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when a general error occurs while communicating with the Integra external system.
 */
public class IntegraServiceException extends RuntimeException {
    public IntegraServiceException(String message) {
        super(message);
    }
    public IntegraServiceException () {

    }
}
