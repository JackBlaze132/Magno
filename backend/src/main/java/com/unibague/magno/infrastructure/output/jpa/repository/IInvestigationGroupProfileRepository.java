package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.domain.model.excel.projections.InvestigationGroupHYRProjection;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IInvestigationGroupProfileRepository extends JpaRepository<InvestigationGroupProfileEntity, Long> {
    List<InvestigationGroupProfileEntity> findByAcademicPeriodId(Long academicPeriodId);

    @Query(value = """
    SELECT
        ap.name AS academicPeriodName,
        ig.name AS investigationGroupName,
        rs.name AS researchSeedbedName,
        u.full_name AS coordinatorName,
        COUNT(rssp.student_profile_id) AS studentCount,
        rsp.was_active AS isActive
    FROM research_seedbeds rs
    JOIN research_seedbeds_profiles rsp ON rs.id = rsp.research_seedbed_id
    JOIN investigation_groups ig ON ig.id = rsp.investigation_group_profile_id
    JOIN academic_periods ap ON ap.id = rsp.academic_period_id
    JOIN users u ON u.id = rsp.coordinator_id
    LEFT JOIN research_seedbeds_student_profiles rssp
           ON rsp.id = rssp.research_seedbed_profile_id
    WHERE ap.id = :academicPeriodId
    GROUP BY rsp.id, ap.name, ig.name, rs.name, u.full_name, rsp.was_active
    ORDER BY ap.name, rs.name
""", nativeQuery = true)
    List<InvestigationGroupHYRProjection> getInvestigationGroupsReportByAcademicPeriodId(@Param("academicPeriodId") Long academicPeriodId);

}
