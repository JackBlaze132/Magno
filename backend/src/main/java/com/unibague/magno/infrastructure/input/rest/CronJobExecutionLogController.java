package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.response.CronJobExecutionLogResponse;
import com.unibague.magno.application.handler.impl.CronJobExecutionLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for querying cron job execution logs in Magno.
 * Provides endpoints for retrieving logs of scheduled task executions,
 * including filtering by job name, status, date range, and recent executions.
 * Used for monitoring the health and status of background jobs.
 *
 * @see CronJobExecutionLogHandler
 */
@RestController
@RequestMapping("/cronjob-logs")
@RequiredArgsConstructor
public class CronJobExecutionLogController {
    private final CronJobExecutionLogHandler cronJobExecutionLogHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<CronJobExecutionLogResponse>> getAllLogs() {
        List<CronJobExecutionLogResponse> responses = cronJobExecutionLogHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/job/{jobName}", headers = "API-VERSION=1")
    public ResponseEntity<List<CronJobExecutionLogResponse>> getLogsByJobName(@PathVariable String jobName) {
        List<CronJobExecutionLogResponse> responses = cronJobExecutionLogHandler.findByJobName(jobName);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/status/{status}", headers = "API-VERSION=1")
    public ResponseEntity<List<CronJobExecutionLogResponse>> getLogsByStatus(@PathVariable String status) {
        List<CronJobExecutionLogResponse> responses = cronJobExecutionLogHandler.findByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/recent/{limit}", headers = "API-VERSION=1")
    public ResponseEntity<List<CronJobExecutionLogResponse>> getRecentLogs(@PathVariable int limit) {
        List<CronJobExecutionLogResponse> responses = cronJobExecutionLogHandler.findRecentExecutions(limit);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/date-range", headers = "API-VERSION=1")
    public ResponseEntity<List<CronJobExecutionLogResponse>> getLogsByDateRange(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        List<CronJobExecutionLogResponse> responses = cronJobExecutionLogHandler.findByDateRange(
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate)
        );
        return ResponseEntity.ok(responses);
    }
}
