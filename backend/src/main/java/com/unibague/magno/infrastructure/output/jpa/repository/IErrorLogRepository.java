package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ErrorLogEntity e WHERE e.id IN :ids")
    int deleteByIds(@Param("ids") List<Long> ids);

    List<ErrorLogEntity> findByTimestampBefore(LocalDateTime timestamp);
}

