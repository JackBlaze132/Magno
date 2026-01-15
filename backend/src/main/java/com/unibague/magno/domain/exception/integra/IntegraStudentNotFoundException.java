package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when a student cannot be found in the Integra external system.
 */
public class IntegraStudentNotFoundException extends RuntimeException {
    public IntegraStudentNotFoundException(String message) {
        super(message);
    }
    public IntegraStudentNotFoundException () {

    }
}
