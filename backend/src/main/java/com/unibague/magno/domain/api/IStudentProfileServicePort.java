package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service port interface that defines the contract for student profile management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to student profiles,
 * which represent student information and participation in research activities for specific
 * academic periods. It includes integration with external systems (Integra) for student data.
 * </p>
 *
 * @see StudentProfile
 * @see User
 */
public interface IStudentProfileServicePort {
    
    /**
     * Retrieves a student profile by its unique identifier.
     *
     * @param id the unique identifier of the student profile
     * @return the student profile with the specified ID
     */
    StudentProfile findById(Long id);
    
    /**
     * Persists a new student profile.
     *
     * @param studentProfile the student profile to save
     * @return the saved student profile
     */
    StudentProfile save(StudentProfile studentProfile);
    
    /**
     * Updates an existing student profile.
     *
     * @param id the unique identifier of the student profile to update
     * @param studentProfile the student profile data to update
     * @return the updated student profile
     */
    StudentProfile update(Long id, StudentProfile studentProfile);
    
    /**
     * Retrieves a student profile by user ID and academic period as an Optional.
     *
     * @param userId the unique identifier of the user
     * @param academicPeriodId the unique identifier of the academic period
     * @return an Optional containing the student profile if found, or empty if not found
     */
    Optional<StudentProfile> findByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
    
    /**
     * Checks if a student profile exists for a specific user and academic period.
     *
     * @param userId the unique identifier of the user
     * @param academicPeriodId the unique identifier of the academic period
     * @return {@code true} if a profile exists for the user and period, {@code false} otherwise
     */
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
    
    /**
     * Deletes a student profile by its unique identifier.
     *
     * @param id the unique identifier of the student profile to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all student profiles in the system.
     *
     * @return a list of all student profiles
     */
    List<StudentProfile> findAll();
    
    /**
     * Retrieves all student profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of student profiles for the specified period
     */
    List<StudentProfile> findAllByAcademicPeriodId(Long academicPeriodId);
    
    /**
     * Gets or creates multiple student profiles from a list of student data.
     * <p>
     * This method processes a list of student data and either retrieves existing profiles
     * or creates new ones as needed for the specified academic period.
     * </p>
     *
     * @param cleanedStudentListOfMaps a list of maps containing cleaned student data
     * @param users a list of user entities to match with student data
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of student profiles (existing or newly created)
     */
    List<StudentProfile> getOrCreateStudentProfiles(List<Map<String, String>> cleanedStudentListOfMaps,
                                                    List<User> users, Long academicPeriodId);
    
    /**
     * Retrieves all student profiles associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of student profiles for the specified user
     */
    List<StudentProfile> findAllProfilesByUserId(Long userId);
    
    /**
     * Gets or creates a single student profile from student data.
     * <p>
     * This method processes student data and either retrieves an existing profile
     * or creates a new one for the specified academic period.
     * </p>
     *
     * @param studentProfileMap a map containing student profile data
     * @param users a list of user entities to match with student data
     * @param academicPeriodId the unique identifier of the academic period
     * @return the student profile (existing or newly created)
     */
    StudentProfile getOrCreateStudentProfile(Map<String, String> studentProfileMap,
                                             List<User> users, Long academicPeriodId);
    
    /**
     * Creates a student profile from external Integra system data.
     * <p>
     * This method fetches student information from the Integra system and creates
     * a corresponding student profile for the specified academic period.
     * </p>
     *
     * @param identification the student's identification number
     * @param academicPeriodId the unique identifier of the academic period
     * @param user the user entity associated with the student
     * @return the created student profile
     */
    StudentProfile createStudentProfileFromIntegraData(
            String identification, Long academicPeriodId, User user);
    
    /**
     * Updates the role assignment for a student profile.
     *
     * @param studentProfileId the unique identifier of the student profile
     * @param roleId the unique identifier of the role to assign
     */
    void updateRoleId(Long studentProfileId, Long roleId);
}
