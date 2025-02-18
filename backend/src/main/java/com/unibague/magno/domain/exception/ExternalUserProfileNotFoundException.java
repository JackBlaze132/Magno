package com.unibague.magno.domain.exception;

public class ExternalUserProfileNotFoundException extends RuntimeException{

    public ExternalUserProfileNotFoundException(String message){
        super(message);
    }

    public ExternalUserProfileNotFoundException(){
    }
}
