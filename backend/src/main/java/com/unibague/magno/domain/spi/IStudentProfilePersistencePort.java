package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.StudentProfile;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing student profile data.
 * <p>
 * This interface defines the contract for persisting and retrieving student profiles,
 * which represent the relationship between a student (user) and an academic period.
 * A student can have multiple profiles across different academic periods.
 * </p>
 */
public interface IStudentProfilePersistencePort {
    Optional<StudentProfile> findById(Long id);
    StudentProfile save(StudentProfile studentProfile);
    StudentProfile update(Long id, StudentProfile studentProfile);

    /**
     * Finds a student profile by user ID and academic period ID.
     *
     * @param userId           the unique identifier of the user
     * @param academicPeriodId the unique identifier of the academic period
     * @return an {@link Optional} containing the student profile if found, or empty otherwise
     */
    Optional<StudentProfile> findByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);

    /**
     * Checks if a student profile exists for a given user and academic period.
     *
     * @param userId           the unique identifier of the user
     * @param academicPeriodId the unique identifier of the academic period
     * @return {@code true} if a profile exists, {@code false} otherwise
     */
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);

    void deleteById(Long id);
    List<StudentProfile> findAll();

    /**
     * Retrieves all student profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of student profiles associated with the academic period
     */
    List<StudentProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    /**
     * Retrieves all profiles associated with a specific user across all academic periods.
     *
     * @param userId the unique identifier of the user
     * @return a list of all student profiles for the user
     */
    List<StudentProfile> findAllProfilesByUserId(Long userId);

    /**
     * Updates the role assigned to a student profile.
     *
     * @param studentProfileId the unique identifier of the student profile
     * @param roleId           the unique identifier of the new role to assign
     */
    void updateRoleId(Long studentProfileId, Long roleId);
}
