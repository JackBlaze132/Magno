package com.unibague.magno.domain.exception.researchseedbedprofile;

/**
 * Exception thrown when a research seedbed profile cannot be found in the system.
 */
public class ResearchSeedbedProfileNotFoundException extends RuntimeException {
    public ResearchSeedbedProfileNotFoundException(String message) {
        super(message);
    }

    public ResearchSeedbedProfileNotFoundException() {

    }
}
