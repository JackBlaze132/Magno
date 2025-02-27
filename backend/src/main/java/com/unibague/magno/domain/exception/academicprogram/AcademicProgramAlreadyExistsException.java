package com.unibague.magno.domain.exception.academicprogram;

public class AcademicProgramAlreadyExistsException extends RuntimeException {
    public AcademicProgramAlreadyExistsException(String message) {
        super(message);
    }
    public AcademicProgramAlreadyExistsException() {
    }
}
