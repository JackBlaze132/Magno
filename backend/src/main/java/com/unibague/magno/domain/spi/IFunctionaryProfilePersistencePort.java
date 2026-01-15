package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing functionary profile data.
 * <p>
 * This interface defines the contract for persisting and retrieving functionary profiles.
 * Functionaries are university staff members (professors, researchers, coordinators)
 * who participate in investigation groups and research seedbeds. A functionary can have
 * multiple profiles across different academic periods.
 * </p>
 */
public interface IFunctionaryProfilePersistencePort {
    Optional<FunctionaryProfile> findById(Long id);
    FunctionaryProfile save(FunctionaryProfile functionaryProfile);
    FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile);
    void deleteById(Long id);
    List<FunctionaryProfile> findAll();

    /**
     * Retrieves all profiles associated with a specific user across all academic periods.
     *
     * @param userId the unique identifier of the user
     * @return a list of all functionary profiles for the user
     */
    List<FunctionaryProfile> findAllProfilesByUserId(Long userId);

    /**
     * Checks if a functionary profile exists for a given user and academic period.
     *
     * @param userId           the unique identifier of the user
     * @param academicPeriodId the unique identifier of the academic period
     * @return {@code true} if a profile exists, {@code false} otherwise
     */
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);

    /**
     * Retrieves all functionary profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of functionary profiles associated with the academic period
     */
    List<FunctionaryProfile> findAllProfilesByAcademicPeriodId(Long academicPeriodId);

    /**
     * Retrieves functionary profiles filtered by both functionary profile ID and academic period.
     *
     * @param functionaryProfileId the unique identifier of the functionary profile
     * @param academicPeriodId     the unique identifier of the academic period
     * @return a list of matching functionary profiles
     */
    List<FunctionaryProfile> findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId
            (Long functionaryProfileId, Long academicPeriodId);
}
