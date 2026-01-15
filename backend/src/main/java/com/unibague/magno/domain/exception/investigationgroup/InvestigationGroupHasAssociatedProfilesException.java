package com.unibague.magno.domain.exception.investigationgroup;

/**
 * Exception thrown when attempting to delete an investigation group that has associated profiles.
 */
public class InvestigationGroupHasAssociatedProfilesException extends RuntimeException {
    public InvestigationGroupHasAssociatedProfilesException(String message) {
        super(message);
    }
}
