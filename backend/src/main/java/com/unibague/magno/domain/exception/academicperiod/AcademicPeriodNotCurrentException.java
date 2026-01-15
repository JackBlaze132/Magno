package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when an operation requires a current academic period but the specified period is not marked as current.
 */
public class AcademicPeriodNotCurrentException extends RuntimeException {
    public AcademicPeriodNotCurrentException(String message) {
        super(message);
    }
}
