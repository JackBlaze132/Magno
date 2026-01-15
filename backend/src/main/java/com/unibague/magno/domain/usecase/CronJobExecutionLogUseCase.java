package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.ICronJobExecutionLogServicePort;
import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.domain.spi.ICronJobExecutionLogPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Use case implementation for managing cron job execution logs.
 * <p>
 * Handles business logic for tracking and querying scheduled task executions.
 * This enables monitoring and auditing of automated processes like data
 * synchronization, cleanup tasks, and periodic reports.
 * </p>
 */
public class CronJobExecutionLogUseCase implements ICronJobExecutionLogServicePort {

    private final ICronJobExecutionLogPersistencePort cronJobExecutionLogPersistencePort;

    public CronJobExecutionLogUseCase(ICronJobExecutionLogPersistencePort cronJobExecutionLogPersistencePort) {
        this.cronJobExecutionLogPersistencePort = cronJobExecutionLogPersistencePort;
    }

    @Override
    public CronJobExecutionLog save(CronJobExecutionLog cronJobExecutionLog) {
        return cronJobExecutionLogPersistencePort.save(cronJobExecutionLog);
    }

    @Override
    public List<CronJobExecutionLog> findAll() {
        return cronJobExecutionLogPersistencePort.findAll();
    }

    @Override
    public List<CronJobExecutionLog> findByJobName(String jobName) {
        return cronJobExecutionLogPersistencePort.findByJobName(jobName);
    }

    @Override
    public List<CronJobExecutionLog> findByStatus(String status) {
        return cronJobExecutionLogPersistencePort.findByStatus(status);
    }

    @Override
    public List<CronJobExecutionLog> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return cronJobExecutionLogPersistencePort.findByDateRange(startDate, endDate);
    }

    @Override
    public List<CronJobExecutionLog> findRecentExecutions(int limit) {
        return cronJobExecutionLogPersistencePort.findRecentExecutions(limit);
    }

    @Override
    public void deleteLogsOlderThanDays(int days) {
        cronJobExecutionLogPersistencePort.deleteLogsOlderThanDays(days);
    }
}
