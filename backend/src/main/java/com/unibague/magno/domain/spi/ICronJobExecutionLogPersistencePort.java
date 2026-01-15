package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.CronJobExecutionLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Persistence port for managing cron job execution log data.
 * <p>
 * This interface defines the contract for persisting and retrieving execution logs
 * of scheduled tasks (cron jobs). It enables monitoring and auditing of automated
 * processes like data synchronization, cleanup tasks, and periodic reports.
 * </p>
 */
public interface ICronJobExecutionLogPersistencePort {

    List<CronJobExecutionLog> findAll();
    CronJobExecutionLog save(CronJobExecutionLog cronJobExecutionLog);

    /**
     * Retrieves all execution logs for a specific cron job.
     *
     * @param jobName the name of the cron job
     * @return a list of execution logs for the specified job
     */
    List<CronJobExecutionLog> findByJobName(String jobName);

    /**
     * Retrieves all execution logs with a specific status.
     *
     * @param status the execution status (e.g., "SUCCESS", "FAILURE")
     * @return a list of execution logs with the specified status
     */
    List<CronJobExecutionLog> findByStatus(String status);

    /**
     * Retrieves execution logs within a specific date range.
     *
     * @param startDate the start of the date range (inclusive)
     * @param endDate   the end of the date range (inclusive)
     * @return a list of execution logs within the specified range
     */
    List<CronJobExecutionLog> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves the most recent cron job executions.
     *
     * @param limit the maximum number of records to return
     * @return a list of the most recent execution logs, ordered by date descending
     */
    List<CronJobExecutionLog> findRecentExecutions(int limit);

    /**
     * Deletes execution logs older than a specified number of days.
     * <p>
     * Used for maintenance to prevent unbounded log growth.
     * </p>
     *
     * @param days the age threshold in days; logs older than this will be deleted
     */
    void deleteLogsOlderThanDays(int days);
}
