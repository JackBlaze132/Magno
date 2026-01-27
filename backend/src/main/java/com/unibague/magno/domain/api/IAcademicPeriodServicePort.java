package com.unibague.magno.domain.api;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.exception.academicperiod.MultipleActiveAcademicPeriodsException;
import com.unibague.magno.domain.model.AcademicPeriod;

import java.util.List;

/**
 * Service port interface that defines the contract for academic period management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to academic periods,
 * which represent time frames in the academic calendar (semesters, terms, etc.).
 * </p>
 *
 * @see AcademicPeriod
 */
public interface IAcademicPeriodServicePort {
    
    /**
     * Retrieves an academic period by its unique identifier.
     *
     * @param id the unique identifier of the academic period
     * @return the academic period with the specified ID
     */
    AcademicPeriod findById(Long id);
    
    /**
     * Retrieves an academic period by its name.
     *
     * @param name the name of the academic period
     * @return the academic period with the specified name
     */
    AcademicPeriod findByName(String name);
    
    /**
     * Persists a new academic period.
     *
     * @param academicPeriod the academic period to save
     * @return the saved academic period
     */
    AcademicPeriod save(AcademicPeriod academicPeriod);
    
    /**
     * Updates an existing academic period.
     *
     * @param id the unique identifier of the academic period to update
     * @param academicPeriod the academic period data to update
     * @return the updated academic period
     */
    AcademicPeriod update(Long id, AcademicPeriod academicPeriod);
    
    /**
     * Deletes an academic period by its unique identifier.
     *
     * @param id the unique identifier of the academic period to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all academic periods in the system.
     *
     * @return a list of all academic periods
     */
    List<AcademicPeriod> findAll();

    /**
     * Retrieves the single active academic period.
     * Throws an exception if there are multiple active periods or none.
     *
     * @return the active academic period
     * @throws MultipleActiveAcademicPeriodsException if multiple active periods exist
     * @throws AcademicPeriodNotFoundException if no active period exists
     */
    AcademicPeriod findActiveAcademicPeriod();
}
