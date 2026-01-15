package com.unibague.magno.domain.exception.academicprogram;

/**
 * Exception thrown when an academic program cannot be found in the system.
 */
public class AcademicProgramNotFoundException extends RuntimeException {
    public AcademicProgramNotFoundException(String message) {
        super(message);
    }

    public AcademicProgramNotFoundException () {

    }
}
