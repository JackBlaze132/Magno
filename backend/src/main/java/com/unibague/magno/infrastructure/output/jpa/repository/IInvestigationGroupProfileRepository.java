package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.domain.model.excel.projections.ActiveSeedbedsHYRProjection;
import com.unibague.magno.domain.model.excel.projections.InvestigationGroupHYRProjection;
import com.unibague.magno.domain.model.excel.projections.InvestigationGroupYRProjection;
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

    @Query(value = """
        SELECT
            CONCAT(p1.name, '__', p2.name) AS academicPeriodName,
            ig.name AS investigationGroupName,
            rs.name AS researchSeedbedName,
            COUNT(DISTINCT sp.user_id) AS studentCount
        FROM research_seedbeds_profiles rsp
        JOIN academic_periods p1 ON p1.id = :academicPeriodId1
        JOIN academic_periods p2 ON p2.id = :academicPeriodId2
        JOIN research_seedbeds rs ON rs.id = rsp.research_seedbed_id
        JOIN investigation_group_profiles igp ON igp.id = rsp.investigation_group_profile_id
        JOIN investigation_groups ig ON ig.id = igp.investigation_group_id
        JOIN research_seedbeds_student_profiles rssp ON rssp.research_seedbed_profile_id = rsp.id
        JOIN student_profiles sp ON sp.id = rssp.student_profile_id
        WHERE rsp.academic_period_id IN (:academicPeriodId1, :academicPeriodId2)
          AND sp.academic_period_id IN (:academicPeriodId1, :academicPeriodId2)
        GROUP BY
            ig.id,
            ig.name,
            rs.id,
            rs.name,
            p1.name,
            p2.name
        ORDER BY
            ig.name,
            rs.name
        """, nativeQuery = true)
    List<InvestigationGroupYRProjection> getInvestigationGroupsReportByAcademicPeriodId1AndAcademicPeriodId2(
            @Param("academicPeriodId1") Long academicPeriodId1,
            @Param("academicPeriodId2") Long academicPeriodId2
    );

    // Currently, this query works only with SQL Server, notice that if another database is used, it may need to be adjusted.
    @Query(value = """
    SELECT
        ap.name AS academicPeriodName,
        ig.name AS investigationGroupName,
        COUNT(DISTINCT rsp.id) AS activeSeedbedsCount,
        COUNT(DISTINCT sp.user_id) AS activeStudentsCount
    FROM research_seedbeds_profiles rsp
    JOIN academic_periods ap
        ON ap.id = rsp.academic_period_id
    JOIN investigation_group_profiles igp
        ON igp.id = rsp.investigation_group_profile_id
    JOIN investigation_groups ig
        ON ig.id = igp.investigation_group_id
    LEFT JOIN research_seedbeds_student_profiles rssp
        ON rssp.research_seedbed_profile_id = rsp.id AND rssp.was_active = 1
    LEFT JOIN student_profiles sp
        ON sp.id = rssp.student_profile_id
    WHERE rsp.academic_period_id = :academicPeriodId
      AND rsp.was_active = 1
    GROUP BY ap.name, ig.name
    ORDER BY ig.name
    """, nativeQuery = true)
    List<ActiveSeedbedsHYRProjection> getActiveSeedbedsReportByAcademicPeriod(@Param("academicPeriodId") Long academicPeriodId);

}
