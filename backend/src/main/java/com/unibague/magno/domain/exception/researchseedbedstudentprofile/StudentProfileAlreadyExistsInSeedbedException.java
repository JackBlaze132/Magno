package com.unibague.magno.domain.exception.researchseedbedstudentprofile;

/**
 * Exception thrown when attempting to add a student profile to a research seedbed where they are already enrolled.
 */
public class StudentProfileAlreadyExistsInSeedbedException extends RuntimeException {
    public StudentProfileAlreadyExistsInSeedbedException(String message) {
        super(message);
    }
}
