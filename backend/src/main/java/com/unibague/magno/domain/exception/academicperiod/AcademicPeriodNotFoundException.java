package com.unibague.magno.domain.exception.academicperiod;

/**
 * Exception thrown when an academic period cannot be found in the system.
 * <p>
 * Default message: "Academic period not found"
 * </p>
 */
public class AcademicPeriodNotFoundException extends RuntimeException {
    public AcademicPeriodNotFoundException() {
        super("Academic period not found");
    }

    public AcademicPeriodNotFoundException(String message) {
        super(message);
    }
}
