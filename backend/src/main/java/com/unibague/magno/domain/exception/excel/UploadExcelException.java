package com.unibague.magno.domain.exception.excel;

/**
 * Exception thrown when an error occurs during Excel file upload or processing.
 */
public class UploadExcelException extends RuntimeException {
    public UploadExcelException(String message) {
        super(message);
    }
    public UploadExcelException() {

    }
}
