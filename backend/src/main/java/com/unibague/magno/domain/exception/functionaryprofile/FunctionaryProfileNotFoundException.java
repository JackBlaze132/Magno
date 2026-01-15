package com.unibague.magno.domain.exception.functionaryprofile;

/**
 * Exception thrown when a functionary profile cannot be found in the system.
 */
public class FunctionaryProfileNotFoundException extends RuntimeException {
    public FunctionaryProfileNotFoundException(String message) {
        super(message);
    }

    public FunctionaryProfileNotFoundException () {

    }
}
