package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.response.CronJobExecutionLogResponse;
import com.unibague.magno.application.handler.interfaces.ICronJobLogExecutionHandler;
import com.unibague.magno.application.mapper.response.CronJobExecutionLogResponseMapper;
import com.unibague.magno.domain.api.ICronJobExecutionLogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronJobExecutionLogHandler implements ICronJobLogExecutionHandler {

    private final ICronJobExecutionLogServicePort cronJobExecutionLogServicePort;
    private final CronJobExecutionLogResponseMapper cronJobExecutionLogResponseMapper;

    @Override
    public List<CronJobExecutionLogResponse> findAll() {
        return cronJobExecutionLogResponseMapper.toResponseList(
                cronJobExecutionLogServicePort.findAll()
        );
    }

    @Override
    public List<CronJobExecutionLogResponse> findByJobName(String jobName) {
        return cronJobExecutionLogResponseMapper.toResponseList(
                cronJobExecutionLogServicePort.findByJobName(jobName)
        );
    }

    @Override
    public List<CronJobExecutionLogResponse> findByStatus(String status) {
        return cronJobExecutionLogResponseMapper.toResponseList(
                cronJobExecutionLogServicePort.findByStatus(status)
        );
    }

    @Override
    public List<CronJobExecutionLogResponse> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return cronJobExecutionLogResponseMapper.toResponseList(
                cronJobExecutionLogServicePort.findByDateRange(startDate, endDate)
        );
    }

    @Override
    public List<CronJobExecutionLogResponse> findRecentExecutions(int limit) {
        return cronJobExecutionLogResponseMapper.toResponseList(
                cronJobExecutionLogServicePort.findRecentExecutions(limit)
        );
    }
}
