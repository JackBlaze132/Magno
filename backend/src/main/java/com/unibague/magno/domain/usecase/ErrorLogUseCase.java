package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IErrorLogServicePort;
import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.domain.spi.IErrorLogPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Use case implementation for managing error logs.
 * <p>
 * Handles business logic for error log operations including saving, querying,
 * and cleanup of system error records. Error logs are used for monitoring,
 * debugging, and audit purposes.
 * </p>
 */
public class ErrorLogUseCase implements IErrorLogServicePort {

    private final IErrorLogPersistencePort errorLogPersistencePort;

    public ErrorLogUseCase(IErrorLogPersistencePort errorLogPersistencePort) {
        this.errorLogPersistencePort = errorLogPersistencePort;
    }

    @Override
    public ErrorLog save(ErrorLog errorLog) {
        return errorLogPersistencePort.save(errorLog);
    }

    @Override
    public List<ErrorLog> findAll() {
        return errorLogPersistencePort.findAll();
    }

    @Override
    public List<ErrorLog> findByUserId(Long userId) {
        return errorLogPersistencePort.findByUserId(userId);
    }

    @Override
    public List<ErrorLog> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return errorLogPersistencePort.findByTimestampBetween(start, end);
    }

    @Override
    public List<ErrorLog> getLogsOlderThanDays(LocalDateTime date) {
        return errorLogPersistencePort.getLogsOlderThanDays(date);
    }

    @Override
    public List<ErrorLog> getLogsOlderThanDays(int days) {
        return errorLogPersistencePort.getLogsOlderThanDays(LocalDateTime.now().minusDays(days));
    }

    @Override
    public void deleteLogsOlderThanDays(int days) {
        List<Long> logIds = errorLogPersistencePort
                .getLogsOlderThanDays(LocalDateTime.now().minusDays(days))
                .stream()
                .map(ErrorLog::getId)
                .toList();

        if (!logIds.isEmpty()) {
            errorLogPersistencePort.deleteByIds(logIds);
        }
    }
}
