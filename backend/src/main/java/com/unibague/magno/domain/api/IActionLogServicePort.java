package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ActionLog;

import java.time.LocalDateTime;
import java.util.List;

public interface IActionLogServicePort {

    ActionLog save(ActionLog actionLog);
    List<ActionLog> findAll();
    List<ActionLog> findByUserId(Long userId);
    List<ActionLog> findByDateRange(LocalDateTime start, LocalDateTime end);
    List<ActionLog> getLogsOlderThanDays(LocalDateTime date);
    void deleteLogsOlderThanDays(int days);
}

