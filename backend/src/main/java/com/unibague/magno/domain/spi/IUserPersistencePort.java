package com.unibague.magno.domain.spi;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.enums.SeedbedRole;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing user data.
 * <p>
 * This interface defines the contract for persisting and retrieving users in the Magno system.
 * Users can be internal (students, functionaries) or external, and may participate in
 * investigation groups and research seedbeds with different roles.
 * </p>
 */
public interface IUserPersistencePort {
    Optional<User> findById(Long id);
    User save(User user);
    User update(Long id, User user);

    /**
     * Finds a user by their identification document number.
     *
     * @param identification the user's identification document number
     * @return an {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findByUserIdentification(String identification);

    void deleteById(Long id);
    List<User> findAll();

    /**
     * Retrieves all users classified as external users.
     *
     * @return a list of external users
     */
    List<User> findAllExternalUsers();

    /**
     * Retrieves all users classified as internal users (students and functionaries).
     *
     * @return a list of internal users
     */
    List<User> findAllInternalUsers();

    /**
     * Finds a user by their email address.
     *
     * @param email the user's email address
     * @return an {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves all users who are functionaries (university staff).
     *
     * @return a list of functionary users
     */
    List<User> findAllFunctionaries();

    /**
     * Retrieves all users who are students.
     *
     * @return a list of student users
     */
    List<User> findAllStudents();

    /**
     * Retrieves a student's participation records in research seedbeds for certificate generation.
     *
     * @param userId            the unique identifier of the user
     * @param researchseedbedId the unique identifier of the research seedbed
     * @return a list of projections containing certificate-relevant participation data
     */
    List<StudentSeedbedCertificateProjection> getStudentParticipationsInSeedbedCertificates(Long userId, Long researchseedbedId);

    /**
     * Generates a PDF certificate for a student's participation in a research seedbed.
     *
     * @param certificate the certificate data containing student and seedbed information
     * @return the generated PDF certificate as a byte array
     * @throws IOException if an error occurs during certificate generation
     */
    byte[] generateStudentSeedbedCertificate(StudentSeedbedCertificate certificate)
            throws IOException;

    /**
     * Retrieves all distinct users who have a specific role in any seedbed.
     *
     * @param seedbedRole the role to filter users by
     * @return a list of users with the specified role (without duplicates)
     */
    List<User> findAllDistinctUsersByRole(SeedbedRole seedbedRole);

    /**
     * Retrieves all investigation group coordinators active in a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of users who are coordinators in the specified period
     */
    List<User> findInvestigationGroupCoordinatorsByAcademicPeriodId(Long academicPeriodId);
}
