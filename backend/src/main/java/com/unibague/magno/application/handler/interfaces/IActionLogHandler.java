package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.response.ActionLogResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IActionLogHandler {
    List<ActionLogResponse> findAll();
    List<ActionLogResponse> findByUserId(Long userId);
    List<ActionLogResponse> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<ActionLogResponse> getLogsOlderThanDays(LocalDateTime date);
}
