package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInvestigationGroupProfileRepository extends JpaRepository<InvestigationGroupProfileEntity, Long> {
    List<InvestigationGroupProfileEntity> findByAcademicPeriodId(Long academicPeriodId);
}
