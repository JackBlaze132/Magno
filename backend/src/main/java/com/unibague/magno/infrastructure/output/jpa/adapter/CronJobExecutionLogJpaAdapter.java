package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.domain.spi.ICronJobExecutionLogPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.CronJobExecutionLogEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.CronJobExecutionLogEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.ICronJobExecutionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CronJobExecutionLogJpaAdapter implements ICronJobExecutionLogPersistencePort {

    private final ICronJobExecutionLogRepository cronJobExecutionLogRepository;
    private final CronJobExecutionLogEntityMapper cronJobExecutionLogEntityMapper;

    @Override
    public CronJobExecutionLog save(CronJobExecutionLog cronJobExecutionLog) {
        CronJobExecutionLogEntity entity = cronJobExecutionLogEntityMapper.toEntity(cronJobExecutionLog);
        CronJobExecutionLogEntity savedEntity = cronJobExecutionLogRepository.save(entity);
        return cronJobExecutionLogEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<CronJobExecutionLog> findAll() {
        return cronJobExecutionLogRepository.findAll()
                .stream()
                .map(cronJobExecutionLogEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CronJobExecutionLog> findByJobName(String jobName) {
        return cronJobExecutionLogRepository.findByJobName(jobName)
                .stream()
                .map(cronJobExecutionLogEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CronJobExecutionLog> findByStatus(String status) {
        return cronJobExecutionLogRepository.findByStatus(status)
                .stream()
                .map(cronJobExecutionLogEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CronJobExecutionLog> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return cronJobExecutionLogRepository.findByTimestampBetween(startDate, endDate)
                .stream()
                .map(cronJobExecutionLogEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CronJobExecutionLog> findRecentExecutions(int limit) {
        return cronJobExecutionLogRepository.findRecentExecutions(limit)
                .stream()
                .map(cronJobExecutionLogEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLogsOlderThanDays(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        cronJobExecutionLogRepository.deleteLogsOlderThan(cutoffDate);
    }
}
