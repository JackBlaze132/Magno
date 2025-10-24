package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ActionLog;

import java.time.LocalDateTime;
import java.util.List;

public interface IActionLogPersistencePort {

    ActionLog save(ActionLog actionLog);
    List<ActionLog> findAll();
    List<ActionLog> findByUserId(Long userId);
    List<ActionLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    void deleteByIds(List<Long> ids);
    List<ActionLog> getLogsOlderThanDays(LocalDateTime date);
}

