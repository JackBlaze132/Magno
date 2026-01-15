package com.unibague.magno.domain.exception.enums;

/**
 * Exception thrown when an invalid enum value is provided in a request.
 */
public class EnumBadRequestException extends RuntimeException {
    public EnumBadRequestException(String message) {
        super(message);
    }

    public EnumBadRequestException () {

    }
}
