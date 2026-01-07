package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFunctionaryProfileRepository extends JpaRepository<FunctionaryProfileEntity, Long> {
    List<FunctionaryProfileEntity> findAllByUser_Id(Long userId);
    boolean existsByUser_IdAndAcademicPeriod_Id(Long userId, Long academicPeriodId);
    List<FunctionaryProfileEntity> findAllByAcademicPeriod_Id(Long academicPeriodId);

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
