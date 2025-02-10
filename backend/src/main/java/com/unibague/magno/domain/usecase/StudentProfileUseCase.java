package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.StudentProfileServicePort;
import com.unibague.magno.domain.exception.StudentProfileNotFoundException;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.spi.StudentProfilePersistencePort;

import java.util.List;

public class StudentProfileUseCase  implements StudentProfileServicePort {

    private final StudentProfilePersistencePort studentProfilePersistencePort;

    public StudentProfileUseCase(StudentProfilePersistencePort studentProfilePersistencePort) {
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
