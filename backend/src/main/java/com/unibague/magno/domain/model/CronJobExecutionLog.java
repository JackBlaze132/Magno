package com.unibague.magno.domain.model;

import java.time.LocalDateTime;

public class CronJobExecutionLog {
    
    private Long id;
    private String jobName;
    private String status; // SUCCESS, FAILED, PARTIAL_SUCCESS
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long durationMs;
    private String details;
    private Integer recordsProcessed;
    private String errorMessage;
    private String stackTrace;
    private LocalDateTime timestamp;

    public CronJobExecutionLog() {
    }

    public CronJobExecutionLog(Long id, String jobName, String status, LocalDateTime startTime, 
                              LocalDateTime endTime, Long durationMs, String details, 
                              Integer recordsProcessed, String errorMessage, String stackTrace, 
                              LocalDateTime timestamp) {
        this.id = id;
        this.jobName = jobName;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMs = durationMs;
        this.details = details;
        this.recordsProcessed = recordsProcessed;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getRecordsProcessed() {
        return recordsProcessed;
    }

    public void setRecordsProcessed(Integer recordsProcessed) {
        this.recordsProcessed = recordsProcessed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
}
