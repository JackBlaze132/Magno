package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ErrorLog;

public interface IErrorLogPersistencePort {

    ErrorLog save(ErrorLog errorLog);
}
