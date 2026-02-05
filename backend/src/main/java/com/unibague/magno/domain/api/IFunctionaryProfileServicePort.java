package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;

/**
 * Service port interface that defines the contract for functionary profile management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to functionary profiles,
 * which represent institutional staff members (professors, coordinators, administrators) participating
 * in research activities for specific academic periods.
 * </p>
 *
 * @see FunctionaryProfile
 */
public interface IFunctionaryProfileServicePort {
    
    /**
     * Retrieves a functionary profile by its unique identifier.
     *
     * @param id the unique identifier of the functionary profile
     * @return the functionary profile with the specified ID
     */
    FunctionaryProfile findById(Long id);
    
    /**
     * Persists a new functionary profile.
     * <p>
     * This method validates that the academic period associated with the profile is visible.
     * If the period is not visible, an {@code AcademicPeriodNotVisibleException} will be thrown.
     * </p>
     *
     * @param functionaryProfile the functionary profile to save
     * @return the saved functionary profile
     * @throws com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotVisibleException
     *         if the academic period is not visible
     */
    FunctionaryProfile save(FunctionaryProfile functionaryProfile);

    /**
     * Persists a new functionary profile, bypassing the academic period visibility check.
     * <p>
     * <strong>WARNING:</strong> This method should only be used for system-level operations
     * such as DIRI (administrator) user creation, where profiles need to be created in
     * non-visible academic periods.
     * </p>
     *
     * @param functionaryProfile the functionary profile to save
     * @return the saved functionary profile
     */
    FunctionaryProfile saveIgnoringPeriodVisibility(FunctionaryProfile functionaryProfile);
    
    /**
     * Updates an existing functionary profile.
     *
     * @param id the unique identifier of the functionary profile to update
     * @param functionaryProfile the functionary profile data to update
     * @return the updated functionary profile
     */
    FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile);
    
    /**
     * Deletes a functionary profile by its unique identifier.
     *
     * @param id the unique identifier of the functionary profile to delete
     */
    void deleteById(Long id);
    
    /**
     * Checks if a functionary profile exists for a specific user and academic period.
     *
     * @param userId the unique identifier of the user
     * @param academicPeriodId the unique identifier of the academic period
     * @return {@code true} if a profile exists for the user and period, {@code false} otherwise
     */
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
    
    /**
     * Retrieves all functionary profiles in the system.
     *
     * @return a list of all functionary profiles
     */
    List<FunctionaryProfile> findAll();
    
    /**
     * Retrieves all functionary profiles associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of functionary profiles for the specified user
     */
    List<FunctionaryProfile> findAllProfilesByUserId(Long userId);
    
    /**
     * Retrieves all functionary profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of functionary profiles for the specified period
     */
    List<FunctionaryProfile> findAllProfilesByAcademicPeriodId(Long academicPeriodId);
    
    /**
     * Retrieves functionary profiles filtered by profile ID and academic period.
     *
     * @param functionaryProfileId the unique identifier of the functionary profile
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of functionary profiles matching the specified criteria
     */
    List<FunctionaryProfile> findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId
            (Long functionaryProfileId, Long academicPeriodId);
}
