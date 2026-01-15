package com.unibague.magno.domain.exception.user;

/**
 * Exception thrown when a functionary attempts to generate a certificate but is not authorized to do so.
 */
public class FunctionaryNotAllowedToGenerateCertificateException extends RuntimeException {
    public FunctionaryNotAllowedToGenerateCertificateException(String message) {
        super(message);
    }
}
