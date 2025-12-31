package com.unibague.magno.domain.exception.user;

public class DiriUserAlreadyExistsException extends RuntimeException {
    public DiriUserAlreadyExistsException(String message) {
        super(message);
    }
}
