package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvestigationGroupRepository extends JpaRepository<InvestigationGroupEntity, Long> {
}
