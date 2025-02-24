package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.exception.StudentProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.StudentProfileNotFoundException;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.spi.IStudentProfilePersistencePort;

import java.util.List;
import java.util.Optional;

public class StudentProfileUseCase  implements IStudentProfileServicePort {

    private final IStudentProfilePersistencePort studentProfilePersistencePort;

    public StudentProfileUseCase(IStudentProfilePersistencePort studentProfilePersistencePort) {
        this.studentProfilePersistencePort = studentProfilePersistencePort;
    }

    @Override
    public StudentProfile findById(Long id) {
        return studentProfilePersistencePort.findById(id)
                .orElseThrow(() -> new StudentProfileNotFoundException(
                        String.format("StudentProfile with ID %d not found", id)
                ));
    }

    @Override
    public StudentProfile save(StudentProfile studentProfile) {
        if (existsByUserIdAndAcademicPeriodId(studentProfile.getUserId(), studentProfile.getAcademicPeriodId())) {
            throw new StudentProfileAlreadyExistsException(
                    String.format("StudentProfile with ID %d could not be saved because it already exists", studentProfile.getId())
            );
        }
        return studentProfilePersistencePort.save(studentProfile);
    }

    @Override
    public StudentProfile update(Long id, StudentProfile studentProfile) {
        if (studentProfilePersistencePort.findById(id).isEmpty()) {
            throw new StudentProfileNotFoundException(
                    String.format("StudentProfile with ID %d could not be updated because it does not exist", id)
            );
        }
        return studentProfilePersistencePort.update(id, studentProfile);
    }

    @Override
    public Optional<StudentProfile> findByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId) {
        return studentProfilePersistencePort.findByUserIdAndAcademicPeriodId(userId, academicPeriodId);
    }

    @Override
    public boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId) {
        return studentProfilePersistencePort.existsByUserIdAndAcademicPeriodId(userId, academicPeriodId);
    }

    @Override
    public void deleteById(Long id) {
        if (studentProfilePersistencePort.findById(id).isEmpty()) {
            throw new StudentProfileNotFoundException(
                    String.format("StudentProfile with ID %d could not be deleted because it does not exist", id)
            );
        }
        studentProfilePersistencePort.deleteById(id);
    }

    @Override
    public List<StudentProfile> findAll() {
        return studentProfilePersistencePort.findAll();
    }
}
