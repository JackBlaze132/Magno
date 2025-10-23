package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.CronJobExecutionLog;

import java.time.LocalDateTime;
import java.util.List;

public interface ICronJobExecutionLogServicePort {

    List<CronJobExecutionLog> findAll();
    CronJobExecutionLog save(CronJobExecutionLog cronJobExecutionLog);
    List<CronJobExecutionLog> findByJobName(String jobName);
    List<CronJobExecutionLog> findByStatus(String status);
    List<CronJobExecutionLog> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<CronJobExecutionLog> findRecentExecutions(int limit);
    void deleteLogsOlderThanDays(int days);
}
