package com.unibague.magno.domain.exception.academicprogram;

/**
 * Exception thrown when attempting to create an academic program that already exists in the system.
 */
public class AcademicProgramAlreadyExistsException extends RuntimeException {
    public AcademicProgramAlreadyExistsException(String message) {
        super(message);
    }
    public AcademicProgramAlreadyExistsException() {
    }
}
