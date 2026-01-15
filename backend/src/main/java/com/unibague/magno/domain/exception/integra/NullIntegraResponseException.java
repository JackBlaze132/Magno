package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when the Integra external system returns a null or empty response.
 */
public class NullIntegraResponseException extends RuntimeException {
    public NullIntegraResponseException(String message) {
        super(message);
    }
    public NullIntegraResponseException() {
    }
}
