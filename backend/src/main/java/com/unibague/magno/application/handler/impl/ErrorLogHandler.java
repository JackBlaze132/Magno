package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.response.ErrorLogResponse;
import com.unibague.magno.application.handler.interfaces.IErrorLogHandler;
import com.unibague.magno.application.mapper.response.ErrorLogResponseMapper;
import com.unibague.magno.domain.api.IErrorLogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorLogHandler implements IErrorLogHandler {

    private final IErrorLogServicePort errorLogServicePort;
    private final ErrorLogResponseMapper errorLogResponseMapper;

    @Override
    public List<ErrorLogResponse> findAll() {
        return errorLogResponseMapper.toResponseList(errorLogServicePort.findAll());
    }

    @Override
    public List<ErrorLogResponse> findByUserId(Long userId) {
        return errorLogResponseMapper.toResponseList(errorLogServicePort.findByUserId(userId));
    }

    @Override
    public List<ErrorLogResponse> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        return errorLogResponseMapper.toResponseList(errorLogServicePort.findByDateRange(start, end));
    }

    @Override
    public List<ErrorLogResponse> getLogsOlderThanDays(LocalDateTime date) {
        return errorLogResponseMapper.toResponseList(errorLogServicePort.getLogsOlderThanDays(date));
    }
}

