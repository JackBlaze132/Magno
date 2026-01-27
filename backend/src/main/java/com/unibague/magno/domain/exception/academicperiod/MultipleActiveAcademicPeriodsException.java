package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when there are multiple academic periods marked as active.
 * The system requires exactly one active academic period at a time.
 */
public class MultipleActiveAcademicPeriodsException extends RuntimeException {
    public MultipleActiveAcademicPeriodsException(String message) {
        super(message);
    }
}

