package com.unibague.magno.domain.exception.security;

public class UnsupportedPrincipalException extends RuntimeException {
    public UnsupportedPrincipalException(String message) {
        super(message);
    }
    public UnsupportedPrincipalException() {
    }
}
