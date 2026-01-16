package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.response.ActionLogResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handler interface for action log operations.
 * Provides access to audit logs that record HTTP requests and responses
 * for monitoring and debugging purposes.
 */
public interface IActionLogHandler {
    List<ActionLogResponse> findAll();

    /**
     * Retrieves all action logs for a specific user.
     *
     * @param userId the user identifier
     * @return list of action logs associated with the user
     */
    List<ActionLogResponse> findByUserId(Long userId);

    /**
     * Retrieves action logs within a specific time range.
     *
     * @param start the start of the time range
     * @param end the end of the time range
     * @return list of action logs within the specified range
     */
    List<ActionLogResponse> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Retrieves action logs older than the specified date.
     * Useful for log cleanup and archival processes.
     *
     * @param date the cutoff date
     * @return list of action logs older than the specified date
     */
    List<ActionLogResponse> getLogsOlderThanDays(LocalDateTime date);
}
