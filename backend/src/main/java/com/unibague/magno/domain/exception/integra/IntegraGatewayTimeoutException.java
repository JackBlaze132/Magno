package com.unibague.magno.domain.exception.integra;

public class IntegraGatewayTimeoutException extends RuntimeException {
    public IntegraGatewayTimeoutException(String message) {
        super(message);
    }
    public IntegraGatewayTimeoutException() {
    }
}
