package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when a request to the Integra external system times out.
 */
public class IntegraGatewayTimeoutException extends RuntimeException {
    public IntegraGatewayTimeoutException(String message) {
        super(message);
    }
    public IntegraGatewayTimeoutException() {
    }
}
