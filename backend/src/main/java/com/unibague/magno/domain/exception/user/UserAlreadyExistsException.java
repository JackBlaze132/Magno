package com.unibague.magno.domain.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException() {
    }
}
