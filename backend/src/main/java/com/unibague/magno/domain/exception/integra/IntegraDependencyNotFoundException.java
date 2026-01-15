package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when a dependency cannot be found in the Integra external system.
 */
public class IntegraDependencyNotFoundException extends RuntimeException {
    public IntegraDependencyNotFoundException(String message) {
        super(message);
    }
    public IntegraDependencyNotFoundException() {

    }
}
