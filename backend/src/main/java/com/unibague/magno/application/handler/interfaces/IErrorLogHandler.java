package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.response.ErrorLogResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IErrorLogHandler {
    List<ErrorLogResponse> findAll();
    List<ErrorLogResponse> findByUserId(Long userId);
    List<ErrorLogResponse> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<ErrorLogResponse> getLogsOlderThanDays(LocalDateTime date);
}

