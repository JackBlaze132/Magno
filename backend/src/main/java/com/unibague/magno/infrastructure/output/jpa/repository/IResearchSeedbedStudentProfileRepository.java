package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IResearchSeedbedStudentProfileRepository extends JpaRepository<ResearchSeedbedStudentProfileEntity, Long> {
    boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId);
    List<ResearchSeedbedStudentProfileEntity> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
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
