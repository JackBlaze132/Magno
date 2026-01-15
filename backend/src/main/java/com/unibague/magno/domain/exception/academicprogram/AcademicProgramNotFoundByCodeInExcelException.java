package com.unibague.magno.domain.exception.academicprogram;

/**
 * Exception thrown when an academic program code specified in an Excel file cannot be found in the system.
 */
public class AcademicProgramNotFoundByCodeInExcelException extends RuntimeException {
    public AcademicProgramNotFoundByCodeInExcelException(String message) {
        super(message);
    }
}
