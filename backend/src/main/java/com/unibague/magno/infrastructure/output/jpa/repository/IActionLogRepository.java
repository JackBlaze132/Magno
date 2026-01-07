package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ActionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IActionLogRepository extends JpaRepository<ActionLogEntity, Long> {

    List<ActionLogEntity> findByUser_Id(Long userId);

    List<ActionLogEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM ActionLogEntity a WHERE a.timestamp < :timestamp")
    List<ActionLogEntity> findByTimestampBefore(@Param("timestamp") LocalDateTime timestamp);

    @Modifying
    @Query("DELETE FROM ActionLogEntity a WHERE a.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}

