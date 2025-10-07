package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileNotFoundException;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IStudentProfilePersistencePort;

import java.util.*;
import java.util.stream.Collectors;

import static com.unibague.magno.domain.usecase.ResearchSeedbedStudentProfileUseCase.IDENTIFICATION;

public class StudentProfileUseCase  implements IStudentProfileServicePort {

    private final IStudentProfilePersistencePort studentProfilePersistencePort;
    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IAcademicProgramServicePort academicProgramServicePort;
    private final IRoleServicePort roleServicePort;

    public StudentProfileUseCase(IStudentProfilePersistencePort studentProfilePersistencePort,
                                 IUserServicePort userServicePort,
                                 IIntegraServicePort integraServicePort,
                                 IAcademicProgramServicePort academicProgramServicePort,
                                 IRoleServicePort roleServicePort) {
        this.studentProfilePersistencePort = studentProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.integraServicePort = integraServicePort;
        this.academicProgramServicePort = academicProgramServicePort;
        this.roleServicePort = roleServicePort;
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
        Long userId = studentProfile.getUserId();
        Long academicPeriodId = studentProfile.getAcademicPeriodId();
        if (existsByUserIdAndAcademicPeriodId(userId, academicPeriodId)) {
            throw new StudentProfileAlreadyExistsException(
                    String.format("StudentProfile with User ID %d could not be saved because it already exists" +
                            "in the academic period with ID %d", userId, academicPeriodId)
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

    @Override
    public List<StudentProfile> findAllByAcademicPeriodId(Long academicPeriodId) {
        return studentProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
    }

    @Override
    public List<StudentProfile> getOrCreateStudentProfiles(List<Map<String, String>> cleanedStudentListOfMaps,
                                                            List<User> users, Long academicPeriodId) {
        List<StudentProfile> newStudentProfiles = cleanedStudentListOfMaps.stream()
                .map(studentProfileMap -> getOrCreateStudentProfile(studentProfileMap, users, academicPeriodId))
                .toList();

        return new ArrayList<>(newStudentProfiles);
    }

    @Override
    public List<StudentProfile> findAllProfilesByUserId(Long userId) {
        return studentProfilePersistencePort.findAllProfilesByUserId(userId);
    }

    @Override
    public StudentProfile getOrCreateStudentProfile(Map<String, String> studentProfileMap,
                                                     List<User> users, Long academicPeriodId) {
        String identification = studentProfileMap.get(IDENTIFICATION);
        User user = userServicePort.findUserByIdentification(users, identification);

        return findByUserIdAndAcademicPeriodId(user.getId(), academicPeriodId)
                .orElseGet(() -> createStudentProfileFromIntegraData(identification, academicPeriodId, user));
    }

    @Override
    public StudentProfile createStudentProfileFromIntegraData(
            String identification, Long academicPeriodId, User user) {

        StudentProfile newStudentProfile = new StudentProfile();

        List<IntegraStudent> integraStudents = integraServicePort.getIntegraStudentRecordsByIdentification(identification);
        newStudentProfile.setSemester(
                integraServicePort.getMaxSemester(integraStudents)
        );

        newStudentProfile.setAcademicPeriodId(academicPeriodId); // Assign the correct academic period
        newStudentProfile.setUserId(user.getId()); // Associate the created user

        Set<String> academicProgramCodes = integraStudents.stream()
                .map(IntegraStudent::getProgramCode)
                .collect(Collectors.toSet());
        Set<Long> academicProgramIds = academicProgramServicePort
                .findAcademicProgramsByAcademicProgramCodes(academicProgramCodes)
                .stream()
                .map(AcademicProgram::getId)
                .collect(Collectors.toSet());

        newStudentProfile.setAcademicProgramsIds(academicProgramIds);
        newStudentProfile.setRoleId(roleServicePort.findByName(SeedbedRole.ESTUDIANTE).getId());

        return save(newStudentProfile);
    }
}
