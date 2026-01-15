package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when attempting to create an academic period that already exists in the system.
 */
public class AcademicPeriodAlreadyExistsException extends RuntimeException {
    public AcademicPeriodAlreadyExistsException(String message) {
        super(message);
    }
}

