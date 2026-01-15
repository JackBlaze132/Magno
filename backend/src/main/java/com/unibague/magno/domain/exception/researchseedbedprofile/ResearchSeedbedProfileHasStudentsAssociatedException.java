package com.unibague.magno.domain.exception.researchseedbedprofile;

/**
 * Exception thrown when attempting to delete a research seedbed profile that has associated students.
 */
public class ResearchSeedbedProfileHasStudentsAssociatedException extends RuntimeException {
    public ResearchSeedbedProfileHasStudentsAssociatedException(String message) {
        super(message);
    }
}
