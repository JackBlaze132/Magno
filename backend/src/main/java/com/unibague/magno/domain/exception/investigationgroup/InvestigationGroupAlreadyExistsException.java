package com.unibague.magno.domain.exception.investigationgroup;

/**
 * Exception thrown when attempting to create an investigation group that already exists in the system.
 */
public class InvestigationGroupAlreadyExistsException extends RuntimeException {
    public InvestigationGroupAlreadyExistsException(String message) {
        super(message);
    }
}

