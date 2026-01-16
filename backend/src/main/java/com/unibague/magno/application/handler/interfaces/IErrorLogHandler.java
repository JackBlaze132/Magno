package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.response.ErrorLogResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handler interface for error log operations.
 * Provides access to application error logs for debugging,
 * monitoring, and incident investigation.
 */
public interface IErrorLogHandler {
    List<ErrorLogResponse> findAll();

    /**
     * Retrieves all error logs associated with a specific user.
     *
     * @param userId the user identifier
     * @return list of error logs for the specified user
     */
    List<ErrorLogResponse> findByUserId(Long userId);

    /**
     * Retrieves error logs within a specific time range.
     *
     * @param start the start of the time range
     * @param end the end of the time range
     * @return list of error logs within the specified range
     */
    List<ErrorLogResponse> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Retrieves error logs older than the specified date.
     * Useful for log cleanup and archival processes.
     *
     * @param date the cutoff date
     * @return list of error logs older than the specified date
     */
    List<ErrorLogResponse> getLogsOlderThanDays(LocalDateTime date);
}

