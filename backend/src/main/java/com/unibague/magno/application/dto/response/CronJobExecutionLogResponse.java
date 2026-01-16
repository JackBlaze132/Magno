package com.unibague.magno.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Response DTO containing cron job execution log information.
 * Captures job execution details including status, duration, processed records,
 * and error information if the job failed.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CronJobExecutionLogResponse {
    
    private Long id;
    private String jobName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long durationMs;
    private String details;
    private Integer recordsProcessed;
    private String errorMessage;
    private String stackTrace;
    private LocalDateTime timestamp;
}
