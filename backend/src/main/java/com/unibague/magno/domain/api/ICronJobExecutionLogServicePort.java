package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.CronJobExecutionLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service port interface that defines the contract for cron job execution log management operations.
 * <p>
 * This interface provides methods for persisting, querying, and managing logs of scheduled
 * cron job executions, enabling monitoring and tracking of automated tasks.
 * </p>
 *
 * @see CronJobExecutionLog
 */
public interface ICronJobExecutionLogServicePort {

    /**
     * Retrieves all cron job execution logs in the system.
     *
     * @return a list of all cron job execution logs
     */
    List<CronJobExecutionLog> findAll();
    
    /**
     * Persists a new cron job execution log entry.
     *
     * @param cronJobExecutionLog the cron job execution log to save
     * @return the saved cron job execution log
     */
    CronJobExecutionLog save(CronJobExecutionLog cronJobExecutionLog);
    
    /**
     * Retrieves all execution logs for a specific cron job.
     *
     * @param jobName the name of the cron job
     * @return a list of execution logs for the specified job
     */
    List<CronJobExecutionLog> findByJobName(String jobName);
    
    /**
     * Retrieves execution logs filtered by execution status.
     *
     * @param status the execution status to filter by
     * @return a list of execution logs with the specified status
     */
    List<CronJobExecutionLog> findByStatus(String status);
    
    /**
     * Retrieves execution logs within a specific date range.
     *
     * @param startDate the start date and time of the range
     * @param endDate the end date and time of the range
     * @return a list of execution logs within the specified date range
     */
    List<CronJobExecutionLog> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Retrieves the most recent cron job executions.
     *
     * @param limit the maximum number of recent executions to retrieve
     * @return a list of the most recent execution logs, limited to the specified count
     */
    List<CronJobExecutionLog> findRecentExecutions(int limit);
    
    /**
     * Deletes execution logs that are older than a specified number of days.
     * <p>
     * This method is used for log cleanup and maintenance operations.
     * </p>
     *
     * @param days the number of days to use as the deletion threshold
     */
    void deleteLogsOlderThanDays(int days);
}
