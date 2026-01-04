package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdentificationNumber(String identificationNumber) throws Exception;
    List<UserEntity> findAllByIdentificationNumber(String identificationNumber);
    List<UserEntity> findAllByIsExternalUserTrue();
    List<UserEntity> findByIsExternalUserFalse();
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.typeOfInternalUser = 'FUNCIONARIO'")
    List<UserEntity> findAllFunctionaries();

    @Query("SELECT u FROM UserEntity u WHERE u.typeOfInternalUser = 'ESTUDIANTE'")
    List<UserEntity> findAllStudents();

    @Query(value = """
    SELECT 
        u.full_name AS studentName, 
        u.identification_number AS identificationNumber, 
        rs.name AS seedbedName, 
        ig.name AS investigationGroupName, 
        ap.start_date AS startDate, 
        ap.end_date AS endDate, 
        u_coord.full_name AS seedbedCoordinatorName,
        u_ig_coord.full_name AS investigationGroupCoordinatorName
    FROM research_seedbeds rs
    INNER JOIN research_seedbeds_profiles rsp
        ON rsp.research_seedbed_id = rs.id
    INNER JOIN research_seedbeds_student_profiles rssp
        ON rssp.research_seedbed_profile_id = rsp.id
    INNER JOIN functionary_profiles fp
        ON fp.id = rsp.coordinator_id
    INNER JOIN users u_coord
        ON u_coord.id = fp.user_id
    INNER JOIN investigation_group_profiles igp
        ON igp.id = rsp.investigation_group_profile_id
    INNER JOIN investigation_groups ig
        ON ig.id = igp.investigation_group_id
    INNER JOIN functionary_profiles fp_ig_coord
        ON fp_ig_coord.id = igp.coordinator_id
    INNER JOIN users u_ig_coord
        ON u_ig_coord.id = fp_ig_coord.user_id
    INNER JOIN student_profiles sp
        ON rssp.student_profile_id = sp.id
    INNER JOIN academic_periods ap
        ON ap.id = sp.academic_period_id
    INNER JOIN users u
        ON sp.user_id = u.id
    WHERE u.id = :userId 
    AND rs.id = :researchSeedbedId
    AND rssp.was_active = true
    AND rsp.was_active = true
    """, nativeQuery = true)
    List<StudentSeedbedCertificateProjection> getStudentParticipationsInSeedbedCertificates(
            @Param("userId") Long userId,
            @Param("researchSeedbedId") Long researchSeedbedId
    );

    @Query("SELECT DISTINCT u FROM UserEntity u " +
            "LEFT JOIN u.functionaryProfiles fp " +
            "LEFT JOIN fp.role fr " +
            "LEFT JOIN u.studentProfileEntities sp " +
            "LEFT JOIN sp.role sr " +
            "WHERE fr.name = :roleName OR sr.name = :roleName")
    List<UserEntity> findAllDistinctUsersByRole(@Param("roleName") SeedbedRole roleName);

    @Query("SELECT DISTINCT u FROM UserEntity u " +
            "JOIN u.functionaryProfiles fp " +
            "JOIN fp.investigationGroup igp " +
            "WHERE igp.academicPeriod.id = :academicPeriodId")
    List<UserEntity> findInvestigationGroupCoordinatorsByAcademicPeriodId(@Param("academicPeriodId") Long academicPeriodId);
}
