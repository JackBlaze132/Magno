package com.unibague.magno.domain.model;

import java.time.LocalDateTime;

public class ActionLog {

    private Long id;
    private String httpMethod;
    private String requestUrl;
    private String requestBody;
    private Integer responseStatus;
    private String responseBody;
    private LocalDateTime timestamp;
    private String userEmail;
    private Long userId;
    private String clientIp;
    private String userAgent;
    private String sessionId;
    private Long executionTimeMs;

    public ActionLog() {
    }

    public ActionLog(Long id, String httpMethod, String requestUrl, String requestBody, 
                    Integer responseStatus, String responseBody, LocalDateTime timestamp, 
                    String userEmail, Long userId, String clientIp, String userAgent, 
                    String sessionId, Long executionTimeMs) {
        this.id = id;
        this.httpMethod = httpMethod;
        this.requestUrl = requestUrl;
        this.requestBody = requestBody;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
        this.timestamp = timestamp;
        this.userEmail = userEmail;
        this.userId = userId;
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.sessionId = sessionId;
        this.executionTimeMs = executionTimeMs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    public Long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(Long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}

