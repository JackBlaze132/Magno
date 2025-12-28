package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;

import java.util.List;

public interface IUserHandler {
    UserResponse findById(Long id);
    UserResponse save(UserRequest user);
    UserResponse save(IntegraUserRequest user);
    UserResponse updateById(Long id, UserRequest user);
    void deleteById(Long id);
    List<UserResponse> findAll();
    List<UserResponse> findAllFunctionariesRegistered();
    List<UserResponse> findAllStudentsRegistered();
    List<UserResponse> findAllExternalUsersRegistered();
    List<String> findAllCountries();

    List<UserResponse> findAllInternalUsersRegistered();
    List<UserResponse> findAllDiriUsers();

    StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId);

    byte[] generateByteStudentSeedbedCertificate(StudentSeedbedCertificateRequest studentSeedbedCertificateRequest)
            throws Exception;
}
