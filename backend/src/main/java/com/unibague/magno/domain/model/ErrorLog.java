package com.unibague.magno.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model representing an error log entry captured by the system.
 */
public class ErrorLog {

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

    public ErrorLog() {
    }

    public ErrorLog(Long id, String errorCode, String errorMessage, String details, String exceptionClassName, 
                   String stackTrace, LocalDateTime timestamp, String httpMethod, String requestUrl, 
                   String requestParams, String clientIp, String userEmail, Long userId, 
                   String userAgent, String sessionId) {
        this.id = id;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.details = details;
        this.exceptionClassName = exceptionClassName;
        this.stackTrace = stackTrace;
        this.timestamp = timestamp;
        this.httpMethod = httpMethod;
        this.requestUrl = requestUrl;
        this.requestParams = requestParams;
        this.clientIp = clientIp;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userAgent = userAgent;
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
