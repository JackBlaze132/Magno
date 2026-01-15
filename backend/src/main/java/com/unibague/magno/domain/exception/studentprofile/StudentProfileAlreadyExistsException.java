package com.unibague.magno.domain.exception.studentprofile;

/**
 * Exception thrown when attempting to create a student profile that already exists for a specific user and academic period.
 */
public class StudentProfileAlreadyExistsException extends RuntimeException {
    public StudentProfileAlreadyExistsException(String message) {
        super(message);
    }
    public StudentProfileAlreadyExistsException() {
    }
}
