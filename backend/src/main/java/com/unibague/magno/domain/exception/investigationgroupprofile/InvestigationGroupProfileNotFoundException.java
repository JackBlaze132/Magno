package com.unibague.magno.domain.exception.investigationgroupprofile;

/**
 * Exception thrown when an investigation group profile cannot be found in the system.
 */
public class InvestigationGroupProfileNotFoundException extends RuntimeException {

    public InvestigationGroupProfileNotFoundException(String message) {
        super(message);
    }

    public InvestigationGroupProfileNotFoundException() {
    }
}
