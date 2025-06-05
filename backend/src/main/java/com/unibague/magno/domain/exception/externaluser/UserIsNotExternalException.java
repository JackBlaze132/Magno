package com.unibague.magno.domain.exception.externaluser;

public class UserIsNotExternalException extends RuntimeException{

    public UserIsNotExternalException(String message) {
        super(message);
    }

    public UserIsNotExternalException() {
    }
}
