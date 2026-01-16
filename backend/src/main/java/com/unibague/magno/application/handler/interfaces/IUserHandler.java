package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;

import java.util.List;

/**
 * Handler interface for user operations.
 * Provides comprehensive user management including integration with Integra system,
 * user categorization by type, and certificate generation capabilities.
 */
public interface IUserHandler {
    UserResponse findById(Long id);
    UserResponse save(UserRequest user);

    /**
     * Creates a user from Integra system data.
     * Fetches user information from Integra using the provided identification.
     *
     * @param user the Integra user request containing identification and type
     * @return the created user response
     */
    UserResponse save(IntegraUserRequest user);
    UserResponse updateById(Long id, UserRequest user);
    void deleteById(Long id);
    List<UserResponse> findAll();

    /**
     * Retrieves all users who have functionary profiles in the system.
     *
     * @return list of users registered as functionaries
     */
    List<UserResponse> findAllFunctionariesRegistered();

    /**
     * Retrieves all users who have student profiles in the system.
     *
     * @return list of users registered as students
     */
    List<UserResponse> findAllStudentsRegistered();

    /**
     * Retrieves all users who have external user profiles in the system.
     *
     * @return list of users registered as external users
     */
    List<UserResponse> findAllExternalUsersRegistered();

    /**
     * Retrieves the list of all countries for external user registration.
     *
     * @return list of country names
     */
    List<String> findAllCountries();

    /**
     * Retrieves all internal university users (students and functionaries).
     *
     * @return list of internal users
     */
    List<UserResponse> findAllInternalUsersRegistered();

    /**
     * Retrieves all users with DIRI (Research Director) permissions.
     *
     * @return list of DIRI users
     */
    List<UserResponse> findAllDiriUsers();

    /**
     * Grants DIRI (Research Director) permissions to a user.
     *
     * @param diriIdentification the user's identification number
     * @return the updated user response
     */
    UserResponse addDiriUser(String diriIdentification);

    /**
     * Generates a participation certificate model for a student in a research seedbed.
     *
     * @param userId the student's user identifier
     * @param researchSeedbedId the research seedbed identifier
     * @return the certificate model with student and seedbed information
     */
    StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId);

    /**
     * Generates a PDF participation certificate as byte array for download.
     *
     * @param studentSeedbedCertificateRequest the certificate generation request
     * @return the PDF certificate as byte array
     * @throws Exception if certificate generation fails
     */
    byte[] generateByteStudentSeedbedCertificate(StudentSeedbedCertificateRequest studentSeedbedCertificateRequest)
            throws Exception;

    /**
     * Revokes DIRI (Research Director) permissions from a user.
     *
     * @param diriIdentification the user's identification number
     */
    void deleteDiriUser(String diriIdentification);
}
