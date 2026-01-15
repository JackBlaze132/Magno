package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IActionLogServicePort;
import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.domain.spi.IActionLogPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Use case implementation for managing action logs.
 * <p>
 * Handles business logic for action log operations including saving, querying,
 * and cleanup of user activity records. Action logs track user activities
 * and system operations for auditing and monitoring purposes.
 * </p>
 */
public class ActionLogUseCase implements IActionLogServicePort {

    private final IActionLogPersistencePort actionLogPersistencePort;

    public ActionLogUseCase(IActionLogPersistencePort actionLogPersistencePort) {
        this.actionLogPersistencePort = actionLogPersistencePort;
    }

    @Override
    public ActionLog save(ActionLog actionLog) {
        return actionLogPersistencePort.save(actionLog);
    }

    @Override
    public List<ActionLog> findAll() {
        return actionLogPersistencePort.findAll();
    }

    @Override
    public List<ActionLog> findByUserId(Long userId) {
        return actionLogPersistencePort.findByUserId(userId);
    }

    @Override
    public List<ActionLog> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return actionLogPersistencePort.findByTimestampBetween(start, end);
    }

    @Override
    public List<ActionLog> getLogsOlderThanDays(LocalDateTime date) {
        return actionLogPersistencePort.getLogsOlderThanDays(date);
    }

    @Override
    public void deleteLogsOlderThanDays(int days) {
        List<Long> logIds = actionLogPersistencePort
                .getLogsOlderThanDays(LocalDateTime.now().minusDays(days))
                .stream()
                .map(ActionLog::getId)
                .toList();

        if (!logIds.isEmpty()) {
            actionLogPersistencePort.deleteByIds(logIds);
        }
    }
}
