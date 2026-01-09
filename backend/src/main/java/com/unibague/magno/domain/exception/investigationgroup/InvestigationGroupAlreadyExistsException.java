package com.unibague.magno.domain.exception.investigationgroup;

public class InvestigationGroupAlreadyExistsException extends RuntimeException {
    public InvestigationGroupAlreadyExistsException(String message) {
        super(message);
    }
}

