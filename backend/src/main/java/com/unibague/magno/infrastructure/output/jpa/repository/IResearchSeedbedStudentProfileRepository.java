package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ResearchSeedbedStudentProfileEntity}.
 */
public interface IResearchSeedbedStudentProfileRepository extends JpaRepository<ResearchSeedbedStudentProfileEntity, Long> {

    /**
     * Checks if a student profile exists in the given research seedbed profile.
     */
    boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId);

    /**
     * Finds all student profiles by research seedbed profile ID.
     */
    List<ResearchSeedbedStudentProfileEntity> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);

    /**
     * Finds all student seedbed profiles by student profile and academic period.
     */
    @Query("""
        SELECT rsp
        FROM ResearchSeedbedStudentProfileEntity rsp
        JOIN rsp.studentProfile sp
        WHERE sp.id = :studentProfileId
          AND sp.academicPeriod.id = :academicPeriodId
    """)
    List<ResearchSeedbedStudentProfileEntity> findAllByStudentProfileIdAndAcademicPeriodId(
            @Param("studentProfileId") Long studentProfileId,
            @Param("academicPeriodId") Long academicPeriodId
    );
}
