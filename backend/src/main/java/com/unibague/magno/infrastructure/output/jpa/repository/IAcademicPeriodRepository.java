package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link AcademicPeriodEntity}.
 */
public interface IAcademicPeriodRepository extends JpaRepository<AcademicPeriodEntity, Long> {

    /**
     * Finds an academic period by its name.
     */
    Optional<AcademicPeriodEntity> findByName(String name);

    /**
     * Retrieves all visible academic periods (excludes hidden administrative periods).
     */
    @Query("SELECT a FROM AcademicPeriodEntity a WHERE a.isVisible = true")
    List<AcademicPeriodEntity> findAllVisible();

    /**
     * Retrieves all active and visible academic periods.
     */
    @Query("SELECT a FROM AcademicPeriodEntity a WHERE a.isCurrent = true AND a.isVisible = true")
    List<AcademicPeriodEntity> findAllActiveAndVisible();
}
