package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ErrorLog;

public interface IErrorLogServicePort {

    ErrorLog save(ErrorLog errorLog);
}
