package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when an operation is attempted on a non-visible academic period.
 * <p>
 * Non-visible academic periods are hidden from regular users and should not allow
 * creation of investigation group profiles or research seedbed profiles.
 * </p>
 */
public class AcademicPeriodNotVisibleException extends RuntimeException {
    public AcademicPeriodNotVisibleException(String message) {
        super(message);
    }
}

