package com.unibague.magno.domain.exception.externaluser;

/**
 * Exception thrown when an external user profile cannot be found in the system.
 */
public class ExternalUserProfileNotFoundException extends RuntimeException{

    public ExternalUserProfileNotFoundException(String message){
        super(message);
    }

    public ExternalUserProfileNotFoundException(){
    }
}
