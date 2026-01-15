package com.unibague.magno.domain.exception.investigationgroupprofile;

/**
 * Exception thrown when attempting to assign a functionary as coordinator when they are already a coordinator of another group.
 */
public class InvestigationGroupProfileFunctionaryIsAlreadyACoordinatorException extends RuntimeException {
    public InvestigationGroupProfileFunctionaryIsAlreadyACoordinatorException(String message) {
        super(message);
    }
}
