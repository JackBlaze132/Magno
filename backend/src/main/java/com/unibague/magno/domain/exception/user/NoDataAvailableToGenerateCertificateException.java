package com.unibague.magno.domain.exception.user;

/**
 * Exception thrown when attempting to generate a certificate but there is insufficient data available.
 */
public class NoDataAvailableToGenerateCertificateException extends RuntimeException {
    public NoDataAvailableToGenerateCertificateException(String message) {
        super(message);
    }
}
