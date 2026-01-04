package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ErrorLog;

import java.time.LocalDateTime;
import java.util.List;

public interface IErrorLogServicePort {

    ErrorLog save(ErrorLog errorLog);
    List<ErrorLog> findAll();
    List<ErrorLog> findByUserId(Long userId);
    List<ErrorLog> findByDateRange(LocalDateTime start, LocalDateTime end);
    List<ErrorLog> getLogsOlderThanDays(LocalDateTime date);
    List<ErrorLog> getLogsOlderThanDays(int days);
    void deleteLogsOlderThanDays(int days);
}

