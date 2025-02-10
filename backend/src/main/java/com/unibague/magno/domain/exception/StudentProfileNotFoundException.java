package com.unibague.magno.domain.exception;

public class StudentProfileNotFoundException extends RuntimeException {
    public StudentProfileNotFoundException(String message) {
        super(message);
    }
    public StudentProfileNotFoundException () {

    }
}
