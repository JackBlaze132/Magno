package com.unibague.magno.domain.exception.security;

/**
 * Exception thrown when an unsupported principal type is encountered during authentication or authorization.
 */
public class UnsupportedPrincipalException extends RuntimeException {
    public UnsupportedPrincipalException(String message) {
        super(message);
    }
    public UnsupportedPrincipalException() {
    }
}
