package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ActionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for {@link ActionLogEntity}.
 */
public interface IActionLogRepository extends JpaRepository<ActionLogEntity, Long> {

    /**
     * Finds action logs by user ID.
     */
    List<ActionLogEntity> findByUser_Id(Long userId);

    /**
     * Finds action logs within a timestamp range.
     */
    List<ActionLogEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Finds action logs older than the specified timestamp.
     */
    @Query("SELECT a FROM ActionLogEntity a WHERE a.timestamp < :timestamp")
    List<ActionLogEntity> findByTimestampBefore(@Param("timestamp") LocalDateTime timestamp);

    /**
     * Deletes action logs by their IDs.
     */
    @Modifying
    @Query("DELETE FROM ActionLogEntity a WHERE a.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}

