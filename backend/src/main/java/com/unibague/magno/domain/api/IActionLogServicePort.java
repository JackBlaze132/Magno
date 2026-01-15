package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ActionLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service port interface that defines the contract for action log management operations.
 * <p>
 * This interface provides methods for persisting, querying, and managing action logs,
 * which record user actions and HTTP requests made within the application for auditing
 * and monitoring purposes.
 * </p>
 *
 * @see ActionLog
 */
public interface IActionLogServicePort {

    /**
     * Persists a new action log entry.
     *
     * @param actionLog the action log to save
     * @return the saved action log
     */
    ActionLog save(ActionLog actionLog);
    
    /**
     * Retrieves all action logs in the system.
     *
     * @return a list of all action logs
     */
    List<ActionLog> findAll();
    
    /**
     * Retrieves all action logs associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of action logs for the specified user
     */
    List<ActionLog> findByUserId(Long userId);
    
    /**
     * Retrieves action logs within a specific date range.
     *
     * @param start the start date and time of the range
     * @param end the end date and time of the range
     * @return a list of action logs within the specified date range
     */
    List<ActionLog> findByDateRange(LocalDateTime start, LocalDateTime end);
    
    /**
     * Retrieves action logs that are older than a specific date.
     *
     * @param date the reference date
     * @return a list of action logs older than the specified date
     */
    List<ActionLog> getLogsOlderThanDays(LocalDateTime date);
    
    /**
     * Deletes action logs that are older than a specified number of days.
     * <p>
     * This method is typically used for log cleanup and maintenance operations.
     * </p>
     *
     * @param days the number of days to use as the deletion threshold
     */
    void deleteLogsOlderThanDays(int days);
}

