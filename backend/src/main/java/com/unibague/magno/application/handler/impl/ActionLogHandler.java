package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.response.ActionLogResponse;
import com.unibague.magno.application.handler.interfaces.IActionLogHandler;
import com.unibague.magno.application.mapper.response.ActionLogResponseMapper;
import com.unibague.magno.domain.api.IActionLogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionLogHandler implements IActionLogHandler {

    private final IActionLogServicePort actionLogServicePort;
    private final ActionLogResponseMapper actionLogResponseMapper;


    @Override
    public List<ActionLogResponse> findAll() {
        return actionLogResponseMapper.toResponseList(actionLogServicePort.findAll());
    }

    @Override
    public List<ActionLogResponse> findByUserId(Long userId) {
        return actionLogResponseMapper.toResponseList(actionLogServicePort.findByUserId(userId));
    }

    @Override
    public List<ActionLogResponse> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        return actionLogResponseMapper.toResponseList(actionLogServicePort.findByDateRange(start, end));
    }

    @Override
    public List<ActionLogResponse> getLogsOlderThanDays(LocalDateTime date) {
        return actionLogResponseMapper.toResponseList(actionLogServicePort.getLogsOlderThanDays(date));
    }
}
