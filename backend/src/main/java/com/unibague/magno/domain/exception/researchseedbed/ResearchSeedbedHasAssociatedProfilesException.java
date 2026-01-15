package com.unibague.magno.domain.exception.researchseedbed;

/**
 * Exception thrown when attempting to delete a research seedbed that has associated profiles.
 */
public class ResearchSeedbedHasAssociatedProfilesException extends RuntimeException {
    public ResearchSeedbedHasAssociatedProfilesException(String message) {
        super(message);
    }
}
