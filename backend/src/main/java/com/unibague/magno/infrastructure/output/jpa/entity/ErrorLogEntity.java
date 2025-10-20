package com.unibague.magno.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "error_logs")
public class ErrorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_code", nullable = false, length = 50)
    private String errorCode;

    @Column(name = "error_message", nullable = false, length = 500)
    private String errorMessage;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "exception_class_name", nullable = false, length = 200)
    private String exceptionClassName;

    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "http_method", length = 10)
    private String httpMethod;

    @Column(name = "request_url", length = 1000)
    private String requestUrl;

    @Column(name = "request_params", columnDefinition = "TEXT")
    private String requestParams;

    @Column(name = "client_ip", length = 45)
    private String clientIp;

    @Column(name = "user_email", length = 255)
    private String userEmail;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_agent", length = 1000)
    private String userAgent;

    @Column(name = "session_id", length = 100)
    private String sessionId;
}
