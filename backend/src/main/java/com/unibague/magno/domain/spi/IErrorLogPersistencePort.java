package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ErrorLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Persistence port for managing error log data.
 * <p>
 * This interface defines the contract for persisting and retrieving error logs.
 * Error logs capture system errors and exceptions for monitoring, debugging,
 * and audit purposes.
 * </p>
 */
public interface IErrorLogPersistencePort {

    ErrorLog save(ErrorLog errorLog);
    List<ErrorLog> findAll();

    /**
     * Retrieves all error logs associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of error logs triggered by or related to the user
     */
    List<ErrorLog> findByUserId(Long userId);

    /**
     * Retrieves error logs within a specific time range.
     *
     * @param start the start of the time range (inclusive)
     * @param end   the end of the time range (inclusive)
     * @return a list of error logs recorded within the specified period
     */
    List<ErrorLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Deletes multiple error logs by their IDs.
     *
     * @param ids the list of error log IDs to delete
     */
    void deleteByIds(List<Long> ids);

    /**
     * Retrieves error logs older than a specified date.
     * <p>
     * Useful for cleanup operations to remove outdated logs.
     * </p>
     *
     * @param date the cutoff date; logs older than this will be returned
     * @return a list of error logs older than the specified date
     */
    List<ErrorLog> getLogsOlderThanDays(LocalDateTime date);
}
