package com.unibague.magno.domain.exception.dependency;

/**
 * Exception thrown when a dependency cannot be found in the system.
 */
public class DependencyNotFoundException extends RuntimeException {
    public DependencyNotFoundException(String message) {
        super(message);
    }

    public DependencyNotFoundException() {
    }
}
