package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.CronJobExecutionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for {@link CronJobExecutionLogEntity}.
 */
public interface ICronJobExecutionLogRepository extends JpaRepository<CronJobExecutionLogEntity, Long> {

    /**
     * Finds execution logs by job name.
     */
    List<CronJobExecutionLogEntity> findByJobName(String jobName);

    /**
     * Finds execution logs by status.
     */
    List<CronJobExecutionLogEntity> findByStatus(String status);

    /**
     * Finds execution logs within a date range.
     */
    List<CronJobExecutionLogEntity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves the most recent job executions, ordered by timestamp descending.
     */
    @Query("SELECT c FROM CronJobExecutionLogEntity c ORDER BY c.timestamp DESC")
    List<CronJobExecutionLogEntity> findRecentExecutions(@Param("limit") int limit);

    /**
     * Deletes logs older than the specified cutoff date.
     */
    @Query("DELETE FROM CronJobExecutionLogEntity c WHERE c.timestamp < :cutoffDate")
    void deleteLogsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}
