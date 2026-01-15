package com.unibague.magno.domain.exception.researchseedbed;

/**
 * Exception thrown when a research seedbed cannot be found in the system.
 */
public class ResearchSeedbedNotFoundException extends RuntimeException {
    public ResearchSeedbedNotFoundException(String message) {
        super(message);
    }

    public ResearchSeedbedNotFoundException () {

    }
}
