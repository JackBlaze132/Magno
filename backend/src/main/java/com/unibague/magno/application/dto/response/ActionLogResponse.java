package com.unibague.magno.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO containing action log information for audit purposes.
 * Captures HTTP request/response details, user information, and execution metrics.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionLogResponse {

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
}

