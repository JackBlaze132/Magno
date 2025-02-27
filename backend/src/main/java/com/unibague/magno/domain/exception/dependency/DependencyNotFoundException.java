package com.unibague.magno.domain.exception.dependency;

public class DependencyNotFoundException extends RuntimeException {
    public DependencyNotFoundException(String message) {
        super(message);
    }

    public DependencyNotFoundException() {
    }
}
