package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IInvestigationGroupRepository extends JpaRepository<InvestigationGroupEntity, Long> {

    @Query("SELECT DISTINCT ig FROM InvestigationGroupEntity ig " +
            "JOIN ig.investigationGroupProfiles igp")
    List<InvestigationGroupEntity> findInvestigationGroupsWithAssociatedProfiles();
}
