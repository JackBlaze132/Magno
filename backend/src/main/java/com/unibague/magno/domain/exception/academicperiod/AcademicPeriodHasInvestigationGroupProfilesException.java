package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when attempting to delete an academic period that has associated investigation group profiles.
 */
public class AcademicPeriodHasInvestigationGroupProfilesException extends RuntimeException {
    public AcademicPeriodHasInvestigationGroupProfilesException(String message) {
        super(message);
    }
}

