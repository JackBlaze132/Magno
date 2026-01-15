package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ActionLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Persistence port for managing action log data.
 * <p>
 * This interface defines the contract for persisting and retrieving action logs.
 * Action logs track user activities and system operations for auditing and
 * monitoring purposes.
 * </p>
 */
public interface IActionLogPersistencePort {

    ActionLog save(ActionLog actionLog);
    List<ActionLog> findAll();

    /**
     * Retrieves all action logs associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of action logs performed by the user
     */
    List<ActionLog> findByUserId(Long userId);

    /**
     * Retrieves action logs within a specific time range.
     *
     * @param start the start of the time range (inclusive)
     * @param end   the end of the time range (inclusive)
     * @return a list of action logs recorded within the specified period
     */
    List<ActionLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Deletes multiple action logs by their IDs.
     *
     * @param ids the list of action log IDs to delete
     */
    void deleteByIds(List<Long> ids);

    /**
     * Retrieves action logs older than a specified date.
     * <p>
     * Useful for cleanup operations to remove outdated logs.
     * </p>
     *
     * @param date the cutoff date; logs older than this will be returned
     * @return a list of action logs older than the specified date
     */
    List<ActionLog> getLogsOlderThanDays(LocalDateTime date);
}
