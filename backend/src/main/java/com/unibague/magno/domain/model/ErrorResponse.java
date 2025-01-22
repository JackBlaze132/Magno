package com.unibague.magno.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private String code;
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    public ErrorResponse(String code, String message, List<String> details, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public ErrorResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
