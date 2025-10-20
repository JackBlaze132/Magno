package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IErrorLogServicePort;
import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.domain.spi.IErrorLogPersistencePort;

public class ErrorLogUseCase implements IErrorLogServicePort {

    private final IErrorLogPersistencePort errorLogPersistencePort;

    public ErrorLogUseCase(IErrorLogPersistencePort errorLogPersistencePort) {
        this.errorLogPersistencePort = errorLogPersistencePort;
    }

    @Override
    public ErrorLog save(ErrorLog errorLog) {
        return errorLogPersistencePort.save(errorLog);
    }
}
