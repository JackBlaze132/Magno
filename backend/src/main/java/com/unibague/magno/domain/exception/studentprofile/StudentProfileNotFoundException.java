package com.unibague.magno.domain.exception.studentprofile;

/**
 * Exception thrown when a student profile cannot be found in the system.
 */
public class StudentProfileNotFoundException extends RuntimeException {
    public StudentProfileNotFoundException(String message) {
        super(message);
    }
    public StudentProfileNotFoundException () {

    }
}
