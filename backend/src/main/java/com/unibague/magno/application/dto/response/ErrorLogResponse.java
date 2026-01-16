package com.unibague.magno.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO containing error log information for debugging and monitoring.
 * Captures exception details, request context, and user information when errors occur.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogResponse {

    private Long id;
    private String errorCode;
    private String errorMessage;
    private String details;
    private String exceptionClassName;
    private String stackTrace;
    private LocalDateTime timestamp;
    private String httpMethod;
    private String requestUrl;
    private String requestParams;
    private String clientIp;
    private String userEmail;
    private Long userId;
    private String userAgent;
    private String sessionId;
}
