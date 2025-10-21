package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ErrorLog;

import java.util.List;

public interface IErrorLogServicePort {

    ErrorLog save(ErrorLog errorLog);
    List<ErrorLog> getLogsOlderThanDays(int days);
    void deleteLogsOlderThanDays(int days);
}

