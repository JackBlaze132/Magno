package com.unibague.magno.domain.exception.investigationgroup;

/**
 * Exception thrown when an investigation group cannot be found in the system.
 */
public class InvestigationGroupNotFoundException extends RuntimeException {
    public InvestigationGroupNotFoundException(String message) {
        super(message);
    }

    public InvestigationGroupNotFoundException(){
    }
}
