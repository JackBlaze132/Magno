package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IErrorLogServicePort;
import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.domain.spi.IErrorLogPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<ErrorLog> getLogsOlderThanDays(int days) {
        return errorLogPersistencePort.getLogsOlderThanDays(LocalDateTime.now().minusDays(days));
    }

    @Override
    public void deleteLogsOlderThanDays(int days) {

        List<Long> logIds = getLogsOlderThanDays(days)
                .stream()
                .map(ErrorLog::getId)
                .toList();

        errorLogPersistencePort.deleteLogsOlderThanDays(logIds);
    }
}

