package com.unibague.magno.domain.api;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service port interface that defines the contract for user management operations.
 * <p>
 * This interface provides comprehensive methods for CRUD operations, user queries, integration
 * with external systems (Integra), certificate generation, and special user management
 * (DIRI users, coordinators, etc.). It handles both internal users (students, functionaries)
 * and external users.
 * </p>
 *
 * @see User
 * @see IntegraStudent
 * @see IntegraFunctionary
 */
public interface IUserServicePort {
    
    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the user with the specified ID
     */
    User findById(Long id);
    
    /**
     * Persists a new user.
     *
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);
    
    /**
     * Updates an existing user.
     *
     * @param id the unique identifier of the user to update
     * @param user the user data to update
     * @return the updated user
     */
    User update(Long id, User user);
    
    /**
     * Retrieves a user by their identification number as an Optional.
     *
     * @param identification the identification number of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUserIdentification(String identification);
    
    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users
     */
    List<User> findAll();
    
    /**
     * Retrieves or creates users from a list of student data maps.
     *
     * @param cleanData a list of maps containing cleaned student data
     * @return a list of users corresponding to the student data
     */
    List<User> getUserListByListOfStudentMaps(List<Map<String, String>> cleanData);
    
    /**
     * Retrieves all unique countries from external user profiles.
     *
     * @return a list of country names
     */
    List<String> findAllCountries();

    /**
     * Retrieves all functionaries registered in the application.
     * <p>
     * These are users with the internal user type set to functionary.
     * </p>
     *
     * @return a list of functionary users
     */
    List<User> findAllFunctionariesRegistered();
    
    /**
     * Retrieves all students registered in the application.
     * <p>
     * These are users with the internal user type set to student.
     * </p>
     *
     * @return a list of student users
     */
    List<User> findAllStudentsRegistered();
    
    /**
     * Retrieves all external users registered in the application.
     * <p>
     * These are users marked as external (not part of the institution).
     * </p>
     *
     * @return a list of external users
     */
    List<User> findAllExternalUsersRegistered();

    /**
     * Gets or creates a user from Integra student data.
     *
     * @param integraStudent the Integra student data
     * @return the user entity corresponding to the Integra student
     */
    User getUserByIntegraStudent(IntegraStudent integraStudent);
    
    /**
     * Gets or creates a user from Integra functionary data.
     *
     * @param integraFunctionary the Integra functionary data
     * @return the user entity corresponding to the Integra functionary
     */
    User getUserByIntegraFunctionary(IntegraFunctionary integraFunctionary);
    
    /**
     * Finds a user by identification number within a list of users.
     *
     * @param users the list of users to search
     * @param identification the identification number to search for
     * @return the user with the specified identification, or null if not found
     */
    User findUserByIdentification(List<User> users, String identification);
    
    /**
     * Maps Integra user request data to a functionary user entity.
     *
     * @param userRequest the Integra user request containing functionary data
     * @return a user entity mapped from the Integra functionary data
     */
    User mapFromIntegraFunctionary(IntegraUserRequest userRequest);
    
    /**
     * Maps Integra user request data to a student user entity.
     *
     * @param userRequest the Integra user request containing student data
     * @return a user entity mapped from the Integra student data
     */
    User mapFromIntegraStudent(IntegraUserRequest userRequest);

    /**
     * Retrieves all internal users (students and functionaries) registered in the application.
     *
     * @return a list of internal users
     */
    List<User> findAllInternalUsersRegistered();

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user
     * @return the user with the specified email
     */
    User findByEmail(String email);

    /**
     * Retrieves student participation data for certificate generation.
     *
     * @param userId the unique identifier of the user
     * @param researchseedbedId the unique identifier of the research seedbed
     * @return a list of certificate projections containing participation data
     */
    List<StudentSeedbedCertificateProjection> getStudentParticipationsInSeedbedCertificates(Long userId, Long researchseedbedId);

    /**
     * Generates a student seedbed participation certificate.
     *
     * @param userId the unique identifier of the user
     * @param researchSeedbedId the unique identifier of the research seedbed
     * @return the generated certificate object
     */
    StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId);

    /**
     * Generates a student seedbed participation certificate as a byte array (PDF).
     *
     * @param studentSeedbedCertificateRequest the certificate request containing user and seedbed IDs
     * @return a byte array containing the PDF certificate
     * @throws Exception if certificate generation fails
     */
    byte[] generateByteStudentSeedbedCertificate(StudentSeedbedCertificateRequest studentSeedbedCertificateRequest)
            throws Exception;

    /**
     * Retrieves all users with DIRI (Research and Innovation Directorate) privileges.
     *
     * @return a list of DIRI users
     */
    List<User> findAllDiriUsers();
    
    /**
     * Adds a user to the DIRI (Research and Innovation Directorate) group.
     *
     * @param diriIdentification the identification number of the user to add
     * @return the user added to the DIRI group
     */
    User addDiriUser(String diriIdentification);

    /**
     * Removes a user from the DIRI (Research and Innovation Directorate) group.
     *
     * @param diriIdentification the identification number of the user to remove
     */
    void deleteDiriUser(String diriIdentification);

    /**
     * Retrieves all investigation group coordinators for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of users who are investigation group coordinators
     */
    List<User> findInvestigationGroupCoordinatorsByAcademicPeriodId(Long academicPeriodId);
}
