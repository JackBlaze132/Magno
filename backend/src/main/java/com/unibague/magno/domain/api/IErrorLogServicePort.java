package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ErrorLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service port interface that defines the contract for error log management operations.
 * <p>
 * This interface provides methods for persisting, querying, and managing error logs,
 * which record exceptions and errors that occur within the application for debugging
 * and monitoring purposes.
 * </p>
 *
 * @see ErrorLog
 */
public interface IErrorLogServicePort {

    /**
     * Persists a new error log entry.
     *
     * @param errorLog the error log to save
     * @return the saved error log
     */
    ErrorLog save(ErrorLog errorLog);
    
    /**
     * Retrieves all error logs in the system.
     *
     * @return a list of all error logs
     */
    List<ErrorLog> findAll();
    
    /**
     * Retrieves all error logs associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of error logs for the specified user
     */
    List<ErrorLog> findByUserId(Long userId);
    
    /**
     * Retrieves error logs within a specific date range.
     *
     * @param start the start date and time of the range
     * @param end the end date and time of the range
     * @return a list of error logs within the specified date range
     */
    List<ErrorLog> findByDateRange(LocalDateTime start, LocalDateTime end);
    
    /**
     * Retrieves error logs that are older than a specific date.
     *
     * @param date the reference date
     * @return a list of error logs older than the specified date
     */
    List<ErrorLog> getLogsOlderThanDays(LocalDateTime date);
    
    /**
     * Retrieves error logs that are older than a specified number of days.
     *
     * @param days the number of days to use as the threshold
     * @return a list of error logs older than the specified number of days
     */
    List<ErrorLog> getLogsOlderThanDays(int days);
    
    /**
     * Deletes error logs that are older than a specified number of days.
     * <p>
     * This method is typically used for log cleanup and maintenance operations.
     * </p>
     *
     * @param days the number of days to use as the deletion threshold
     */
    void deleteLogsOlderThanDays(int days);
}

