package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.response.CronJobExecutionLogResponse;
import com.unibague.magno.domain.model.CronJobExecutionLog;

import java.time.LocalDateTime;
import java.util.List;

public interface ICronJobLogExecutionHandler {
    List<CronJobExecutionLogResponse> findAll();
    List<CronJobExecutionLogResponse> findByJobName(String jobName);
    List<CronJobExecutionLogResponse> findByStatus(String status);
    List<CronJobExecutionLogResponse> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<CronJobExecutionLogResponse> findRecentExecutions(int limit);
}
