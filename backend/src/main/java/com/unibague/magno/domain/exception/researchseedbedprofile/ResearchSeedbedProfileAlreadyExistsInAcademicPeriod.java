package com.unibague.magno.domain.exception.researchseedbedprofile;

/**
 * Exception thrown when attempting to create a research seedbed profile that already exists for a specific academic period.
 */
public class ResearchSeedbedProfileAlreadyExistsInAcademicPeriod extends RuntimeException {
    public ResearchSeedbedProfileAlreadyExistsInAcademicPeriod(String message) {
        super(message);
    }
}
