package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IStudentProfileRepository extends JpaRepository<StudentProfileEntity, Long> {
    @Query("SELECT sp FROM StudentProfileEntity sp " +
            "JOIN sp.researchSeedbedStudentProfiles rssp " +
            "WHERE sp.user.identificationNumber = :identification " +
            "AND rssp.researchSeedbedProfile.id = :researchSeedbedProfileId")
    Optional<StudentProfileEntity> findByStudentProfileIdentificationAndResearchSeedbedProfileId(
            @Param("identification") String identification,
            @Param("researchSeedbedProfileId") Long researchSeedbedProfileId
    );
}
