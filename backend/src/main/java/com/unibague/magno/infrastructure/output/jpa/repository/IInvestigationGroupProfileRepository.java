package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvestigationGroupProfileRepository extends JpaRepository<InvestigationGroupProfileEntity, Long> {
}
