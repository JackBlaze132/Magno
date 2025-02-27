package com.unibague.magno.domain.exception.studentprofile;

public class StudentProfileAlreadyExistsException extends RuntimeException {
    public StudentProfileAlreadyExistsException(String message) {
        super(message);
    }
    public StudentProfileAlreadyExistsException() {
    }
}
