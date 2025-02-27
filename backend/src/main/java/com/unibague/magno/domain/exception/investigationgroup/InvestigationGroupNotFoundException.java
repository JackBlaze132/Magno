package com.unibague.magno.domain.exception.investigationgroup;

public class InvestigationGroupNotFoundException extends RuntimeException {
    public InvestigationGroupNotFoundException(String message) {
        super(message);
    }

    public InvestigationGroupNotFoundException(){
    }
}
