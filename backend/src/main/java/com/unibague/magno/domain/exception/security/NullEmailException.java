package com.unibague.magno.domain.exception.security;

public class NullEmailException extends RuntimeException {
    public NullEmailException(String message) {
        super(message);
    }
    public NullEmailException() {
    }
}
