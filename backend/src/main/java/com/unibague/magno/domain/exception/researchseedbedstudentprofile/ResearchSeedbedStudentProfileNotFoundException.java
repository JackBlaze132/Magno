package com.unibague.magno.domain.exception.researchseedbedstudentprofile;

/**
 * Exception thrown when a research seedbed student profile cannot be found in the system.
 */
public class ResearchSeedbedStudentProfileNotFoundException extends RuntimeException {
    public ResearchSeedbedStudentProfileNotFoundException(String message) {
        super(message);
    }
    public ResearchSeedbedStudentProfileNotFoundException() {
    }
}
