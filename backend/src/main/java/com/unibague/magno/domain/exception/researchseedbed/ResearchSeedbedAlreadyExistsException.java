package com.unibague.magno.domain.exception.researchseedbed;

/**
 * Exception thrown when attempting to create a research seedbed that already exists in the system.
 */
public class ResearchSeedbedAlreadyExistsException extends RuntimeException {
    public ResearchSeedbedAlreadyExistsException(String message) {
        super(message);
    }
}

