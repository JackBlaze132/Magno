package com.unibague.magno.domain.exception;

public class InvestigationGroupNotFoundException extends RuntimeException {
    public InvestigationGroupNotFoundException(String message) {
        super(message);
    }

    public InvestigationGroupNotFoundException(){
    }
}
