package com.unibague.magno.domain.exception.academicperiod;

public class AcademicPeriodNotFoundException extends RuntimeException {
    public AcademicPeriodNotFoundException() {
        super("Academic period not found");
    }

    public AcademicPeriodNotFoundException(String message) {
        super(message);
    }
}
