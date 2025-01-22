package com.unibague.magno.domain.exception;

public class AcademicPeriodNotFoundException extends RuntimeException {
    public AcademicPeriodNotFoundException() {
        super("Academic period not found");
    }
}
