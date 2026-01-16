package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for {@link FunctionaryProfileEntity}.
 */
public interface IFunctionaryProfileRepository extends JpaRepository<FunctionaryProfileEntity, Long> {

    /**
     * Finds all functionary profiles by user ID.
     */
    List<FunctionaryProfileEntity> findAllByUser_Id(Long userId);

    /**
     * Checks if a functionary profile exists for the given user and academic period.
     */
    boolean existsByUser_IdAndAcademicPeriod_Id(Long userId, Long academicPeriodId);

    /**
     * Finds all functionary profiles by academic period ID.
     */
    List<FunctionaryProfileEntity> findAllByAcademicPeriod_Id(Long academicPeriodId);

    /**
     * Finds functionary profiles by profile ID and academic period.
     */
    @Query("""
        SELECT fp
        FROM FunctionaryProfileEntity fp
        WHERE fp.id = :functionaryProfileId
          AND fp.academicPeriod.id = :academicPeriodId
    """)
    List<FunctionaryProfileEntity> findAllByIdAndAcademicPeriod(
            @Param("functionaryProfileId") Long functionaryProfileId,
            @Param("academicPeriodId") Long academicPeriodId
    );
}
