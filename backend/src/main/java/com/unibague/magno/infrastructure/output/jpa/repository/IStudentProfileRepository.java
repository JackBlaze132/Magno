package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link StudentProfileEntity}.
 */
public interface IStudentProfileRepository extends JpaRepository<StudentProfileEntity, Long> {

    /**
     * Finds a student profile by user ID and academic period ID.
     */
    @Query("SELECT sp from StudentProfileEntity sp " +
            "WHERE sp.user.id = :userId " +
            "AND sp.academicPeriod.id = :academicPeriodId")
    Optional<StudentProfileEntity> findByUserIdAndAcademicPeriodId(@Param("userId") Long userId,
                                                                   @Param("academicPeriodId") Long academicPeriodId);

    /**
     * Checks if a student profile exists for the given user and academic period.
     */
    boolean existsByUser_IdAndAcademicPeriod_Id(Long userId, Long academicPeriodId);

    /**
     * Finds all student profiles by academic period ID.
     */
    List<StudentProfileEntity> findAllByAcademicPeriod_Id(Long academicPeriodId);

    /**
     * Finds all student profiles by user ID.
     */
    List<StudentProfileEntity> findAllByUser_Id(Long userId);

    /**
     * Updates the role of a student profile.
     */
    @Modifying
    @Query("UPDATE StudentProfileEntity sp SET sp.role.id = :roleId WHERE sp.id = :studentProfileId")
    void updateRoleId(@Param("studentProfileId") Long studentProfileId, @Param("roleId") Long roleId);
}
