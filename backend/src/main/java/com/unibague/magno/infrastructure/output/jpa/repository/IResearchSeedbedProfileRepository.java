package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.domain.model.excel.projections.SeedbedReportProjection;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ResearchSeedbedProfileEntity}.
 * Includes methods for generating Excel reports on seedbed activities.
 */
public interface IResearchSeedbedProfileRepository extends JpaRepository<ResearchSeedbedProfileEntity, Long> {

    /**
     * Finds all research seedbed profiles by investigation group profile ID.
     */
    List<ResearchSeedbedProfileEntity> findAllByInvestigationGroupProfileId(Long id);

    @Query(value = """
    SELECT 
        ap.name AS academicPeriodName,
        ig.name AS investigationGroupName,
        rs.name AS researchSeedbedName,
        u_coordinator.full_name AS coordinatorName,
        u_student.full_name AS studentName,
        u_student.user_code AS code,
        apg.name AS academicProgramName,
        sp.semester AS semester,
        u_student.sex AS sex
    FROM research_seedbeds_profiles rsp
    INNER JOIN academic_periods ap ON rsp.academic_period_id = ap.id
    INNER JOIN investigation_group_profiles igp ON rsp.investigation_group_profile_id = igp.id
    INNER JOIN investigation_groups ig ON ig.id = igp.investigation_group_id
    INNER JOIN research_seedbeds rs ON rsp.research_seedbed_id = rs.id
    INNER JOIN functionary_profiles fp ON fp.id = rsp.coordinator_id
    INNER JOIN users u_coordinator ON u_coordinator.id = fp.user_id
    INNER JOIN research_seedbeds_student_profiles rssp ON rssp.research_seedbed_profile_id = rsp.id
    INNER JOIN student_profiles sp ON sp.id = rssp.student_profile_id
    INNER JOIN users u_student ON u_student.id = sp.user_id
    INNER JOIN student_profiles_academic_programs spap ON spap.student_profile_id = sp.id
    INNER JOIN academic_programs apg ON apg.id = spap.academic_program_id
    WHERE 
        rsp.id IS NOT NULL
        AND ap.id = :apId
        AND rssp.was_active = TRUE
""", nativeQuery = true)
    List<SeedbedReportProjection> getSeedbedReportById(
            @Param("rspId") Long researchSeedbedProfileId, @Param("apId") Long academicPeriodId);

    List<ResearchSeedbedProfileEntity> findAllByAcademicPeriod_Id(Long academicPeriodId);
}
