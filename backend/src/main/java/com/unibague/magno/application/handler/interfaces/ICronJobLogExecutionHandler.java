package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.response.CronJobExecutionLogResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handler interface for cron job execution log operations.
 * Provides access to scheduled task execution history for monitoring
 * job performance and troubleshooting failures.
 */
public interface ICronJobLogExecutionHandler {
    List<CronJobExecutionLogResponse> findAll();

    /**
     * Retrieves execution logs for a specific cron job.
     *
     * @param jobName the name of the cron job
     * @return list of execution logs for the specified job
     */
    List<CronJobExecutionLogResponse> findByJobName(String jobName);

    /**
     * Retrieves execution logs filtered by status (e.g., SUCCESS, FAILURE).
     *
     * @param status the execution status to filter by
     * @return list of execution logs with the specified status
     */
    List<CronJobExecutionLogResponse> findByStatus(String status);

    /**
     * Retrieves execution logs within a specific date range.
     *
     * @param startDate the start of the date range
     * @param endDate the end of the date range
     * @return list of execution logs within the specified range
     */
    List<CronJobExecutionLogResponse> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves the most recent execution logs.
     *
     * @param limit maximum number of records to return
     * @return list of the most recent execution logs
     */
    List<CronJobExecutionLogResponse> findRecentExecutions(int limit);
}
