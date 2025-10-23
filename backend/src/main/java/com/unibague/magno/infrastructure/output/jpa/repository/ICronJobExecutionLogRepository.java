package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.CronJobExecutionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ICronJobExecutionLogRepository extends JpaRepository<CronJobExecutionLogEntity, Long> {
    
    List<CronJobExecutionLogEntity> findByJobName(String jobName);
    List<CronJobExecutionLogEntity> findByStatus(String status);
    List<CronJobExecutionLogEntity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT c FROM CronJobExecutionLogEntity c ORDER BY c.timestamp DESC")
    List<CronJobExecutionLogEntity> findRecentExecutions(@Param("limit") int limit);
    
    @Query("DELETE FROM CronJobExecutionLogEntity c WHERE c.timestamp < :cutoffDate")
    void deleteLogsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}
