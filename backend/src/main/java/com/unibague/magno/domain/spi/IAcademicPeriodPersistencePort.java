package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.AcademicPeriod;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing academic period data.
 * <p>
 * This interface defines the contract for persisting and retrieving academic periods.
 * Academic periods (e.g., semesters) are fundamental organizational units in Magno,
 * as profiles, activities, and reports are all scoped to specific academic periods.
 * </p>
 */
public interface IAcademicPeriodPersistencePort {
    Optional<AcademicPeriod> findById(Long id);

    /**
     * Finds an academic period by its name.
     *
     * @param name the name of the academic period (e.g., "2024-A", "2024-B")
     * @return an {@link Optional} containing the academic period if found, or empty otherwise
     */
    Optional<AcademicPeriod> findByName(String name);

    AcademicPeriod save(AcademicPeriod academicPeriod);
    AcademicPeriod update(Long id, AcademicPeriod academicPeriod);
    void deleteById(Long id);
    List<AcademicPeriod> findAll();
}
