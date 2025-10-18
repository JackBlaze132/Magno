package com.unibague.magno.domain.exception.researchseedbedstudentprofile;

public class StudentProfileAlreadyExistsInSeedbedException extends RuntimeException {
    public StudentProfileAlreadyExistsInSeedbedException(String message) {
        super(message);
    }
}
