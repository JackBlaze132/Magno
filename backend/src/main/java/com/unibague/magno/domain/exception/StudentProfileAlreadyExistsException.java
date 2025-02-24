package com.unibague.magno.domain.exception;

public class StudentProfileAlreadyExistsException extends RuntimeException {
    public StudentProfileAlreadyExistsException(String message) {
        super(message);
    }
    public StudentProfileAlreadyExistsException() {
    }
}
