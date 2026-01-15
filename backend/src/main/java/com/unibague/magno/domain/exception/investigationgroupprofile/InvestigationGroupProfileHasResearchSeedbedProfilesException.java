package com.unibague.magno.domain.exception.investigationgroupprofile;

/**
 * Exception thrown when attempting to delete an investigation group profile that has associated research seedbed profiles.
 */
public class InvestigationGroupProfileHasResearchSeedbedProfilesException extends RuntimeException {
    public InvestigationGroupProfileHasResearchSeedbedProfilesException(String message) {
        super(message);
    }
}

