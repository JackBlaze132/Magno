package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when attempting to create or update an academic period with an end date that precedes the start date.
 */
public class EndDateBeforeStartDateException extends RuntimeException {
    public EndDateBeforeStartDateException(String message) {
        super(message);
    }
}
