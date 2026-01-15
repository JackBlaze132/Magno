package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when an academic program cannot be found in the Integra external system.
 */
public class IntegraAcademicProgramNotFoundException extends RuntimeException {
    public IntegraAcademicProgramNotFoundException(String message) {
        super(message);
    }
    public IntegraAcademicProgramNotFoundException() {

    }
}
