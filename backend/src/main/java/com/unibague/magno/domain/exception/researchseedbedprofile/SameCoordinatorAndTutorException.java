package com.unibague.magno.domain.exception.researchseedbedprofile;

/**
 * Exception thrown when attempting to assign the same person as both coordinator and tutor for a research seedbed profile.
 */
public class SameCoordinatorAndTutorException extends RuntimeException {
    public SameCoordinatorAndTutorException(String message) {
        super(message);
    }
}
