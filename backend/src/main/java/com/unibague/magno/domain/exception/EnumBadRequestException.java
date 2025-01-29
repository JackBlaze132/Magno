package com.unibague.magno.domain.exception;

public class EnumBadRequestException extends RuntimeException {
    public EnumBadRequestException(String message) {
        super(message);
    }

    public EnumBadRequestException () {

    }
}
