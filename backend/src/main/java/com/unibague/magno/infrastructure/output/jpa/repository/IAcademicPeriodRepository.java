package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAcademicPeriodRepository extends JpaRepository<AcademicPeriodEntity, Long> {
    Optional<AcademicPeriodEntity> findByName(String name);

    @Query("SELECT a FROM AcademicPeriodEntity a WHERE a.isVisible = true")
    List<AcademicPeriodEntity> findAllVisible();
}
