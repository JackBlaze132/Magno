package com.unibague.magno.domain.exception.investigationgroupprofile;

/**
 * Exception thrown when attempting to create an investigation group profile that already exists for the same academic period.
 */
public class InvestigationGroupProfileDuplicatedInSameAcademicPeriodException extends RuntimeException {
    public InvestigationGroupProfileDuplicatedInSameAcademicPeriodException(String message) {
        super(message);
    }
}
