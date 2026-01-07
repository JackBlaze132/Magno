package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {

    List<ErrorLogEntity> findByUser_Id(Long userId);

    List<ErrorLogEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT e FROM ErrorLogEntity e WHERE e.timestamp < :timestamp")
    List<ErrorLogEntity> findByTimestampBefore(@Param("timestamp") LocalDateTime timestamp);

    @Modifying
    @Query("DELETE FROM ErrorLogEntity e WHERE e.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}

