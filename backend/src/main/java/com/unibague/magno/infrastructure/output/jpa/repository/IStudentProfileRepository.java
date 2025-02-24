package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IStudentProfileRepository extends JpaRepository<StudentProfileEntity, Long> {

    @Query("SELECT sp from StudentProfileEntity sp " +
            "WHERE sp.user.id = :userId " +
            "AND sp.academicPeriod.id = :academicPeriodId")
    Optional<StudentProfileEntity> findByUserIdAndAcademicPeriodId(@Param("userId") Long userId,
                                                                   @Param("academicPeriodId") Long academicPeriodId);

    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN TRUE ELSE FALSE END " +
            "FROM StudentProfileEntity sp " +
            "WHERE sp.user.id = :userId " +
            "AND sp.academicPeriod.id = :academicPeriodId")
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
}
