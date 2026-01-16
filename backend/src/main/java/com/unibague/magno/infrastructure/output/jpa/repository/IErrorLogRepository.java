package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for {@link ErrorLogEntity}.
 */
public interface IErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {

    /**
     * Finds error logs by user ID.
     */
    List<ErrorLogEntity> findByUser_Id(Long userId);

    /**
     * Finds error logs within a timestamp range.
     */
    List<ErrorLogEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Finds error logs older than the specified timestamp.
     */
    @Query("SELECT e FROM ErrorLogEntity e WHERE e.timestamp < :timestamp")
    List<ErrorLogEntity> findByTimestampBefore(@Param("timestamp") LocalDateTime timestamp);

    /**
     * Deletes error logs by their IDs.
     */
    @Modifying
    @Query("DELETE FROM ErrorLogEntity e WHERE e.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}

