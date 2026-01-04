package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ErrorLog;

import java.time.LocalDateTime;
import java.util.List;

public interface IErrorLogPersistencePort {

    ErrorLog save(ErrorLog errorLog);
    List<ErrorLog> findAll();
    List<ErrorLog> findByUserId(Long userId);
    List<ErrorLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    void deleteByIds(List<Long> ids);
    List<ErrorLog> getLogsOlderThanDays(LocalDateTime date);
}

