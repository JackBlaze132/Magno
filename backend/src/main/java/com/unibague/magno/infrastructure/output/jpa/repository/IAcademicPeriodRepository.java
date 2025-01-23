package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAcademicPeriodRepository extends JpaRepository<AcademicPeriodEntity, Long> {
}
