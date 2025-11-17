package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;

import java.util.List;
import java.util.Optional;

public interface IUserPersistencePort {
    Optional<User> findById(Long id);
    User save(User user);
    User update(Long id, User user);
    Optional<User> findByUserIdentification(String identification);
    void deleteById(Long id);
    List<User> findAll();
    List<User> findAllExternalUsers();

    List<User> findAllInternalUsers();

    Optional<User> findByEmail(String email);

    List<User> findAllFunctionaries();

    List<User> findAllStudents();

    List<StudentSeedbedCertificateProjection> getStudentParticipationsInSeedbedCertificates(Long userId, Long researchseedbedId);
}
