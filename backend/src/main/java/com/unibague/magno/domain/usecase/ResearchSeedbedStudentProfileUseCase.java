package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.ResearchSeedbedStudentProfileNotFoundException;
import com.unibague.magno.domain.exception.UserNotFoundException;
import com.unibague.magno.domain.exception.integra.IntegraStudentNotFoundException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IResearchSeedbedStudentProfilePersistencePort;

import java.util.*;
import java.util.stream.Collectors;

public class ResearchSeedbedStudentProfileUseCase implements IResearchSeedbedStudentProfileServicePort {

    private final IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort;
    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IStudentProfileServicePort studentProfileServicePort;
    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final IAcademicProgramServicePort academicProgramServicePort;

    private static final String IDENTIFICATION = "identification";

    public ResearchSeedbedStudentProfileUseCase(
            IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort,
            IUserServicePort userServicePort,
            IIntegraServicePort integraServicePort,
            IStudentProfileServicePort studentProfileServicePort,
            IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort,
            IAcademicProgramServicePort academicProgramServicePort) {
        this.researchSeedbedStudentProfilePersistencePort = researchSeedbedStudentProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.integraServicePort = integraServicePort;
        this.studentProfileServicePort = studentProfileServicePort;
        this.researchSeedbedProfileServicePort = researchSeedbedProfileServicePort;
        this.academicProgramServicePort = academicProgramServicePort;
    }

    @Override
    public ResearchSeedbedStudentProfile findById(Long id) {
        return researchSeedbedStudentProfilePersistencePort.findById(id)
                .orElseThrow(() -> new ResearchSeedbedStudentProfileNotFoundException(
                        String.format("ResearchSeedbedStudentProfile with id %d not found", id)));
    }

    @Override
    public ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {
        return researchSeedbedStudentProfilePersistencePort.save(researchSeedbedStudentProfile);
    }

    @Override
    public ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {
        return researchSeedbedStudentProfilePersistencePort.update(id, researchSeedbedStudentProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedStudentProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedStudentProfileNotFoundException(
                    String.format("ResearchSeedbedStudentProfile with id %d not found", id));
        }
        researchSeedbedStudentProfilePersistencePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedStudentProfile> findAll() {
        return researchSeedbedStudentProfilePersistencePort.findAll();
    }

    @Override
    public List<ResearchSeedbedStudentProfile> saveAllByExcel(Long researchSeedbedProfileId,
                                                              List<Map<String, String>> researchSeedbedStudentProfiles) {

        Long academicPeriodId = researchSeedbedProfileServicePort.findById(researchSeedbedProfileId).getId();

        // Clean the list because some maps can have empty values
        List<Map<String, String>> cleanedStudentListOfMaps = getCleanedStudentListOfMaps(researchSeedbedStudentProfiles);

        // Verify and register users if they don't exist
        List<User> users = getUserListByListOfMaps(cleanedStudentListOfMaps);

        // Get or create the student profiles if they don't exist
        List<StudentProfile> allStudentProfiles = getOrCreateStudentProfiles(cleanedStudentListOfMaps, users, academicPeriodId);

        // Create and return the research seedbed profiles
        return createResearchSeedbedStudentProfiles(allStudentProfiles, researchSeedbedProfileId);
    }

    private ResearchSeedbedStudentProfile getResearchSeedbedStudentProfile(
            Long researchSeedbedProfileId, Long studentProfileId) {
        ResearchSeedbedStudentProfile researchSeedbedStudentProfile = new ResearchSeedbedStudentProfile();
        researchSeedbedStudentProfile.setStudentProfileId(studentProfileId);
        researchSeedbedStudentProfile.setResearchSeedbedProfileId(researchSeedbedProfileId);
        researchSeedbedStudentProfile.setWasActive(false);
        return researchSeedbedStudentProfilePersistencePort.save(researchSeedbedStudentProfile);
    }

    private StudentProfile createStudentProfileFromIntegraData(
            String identification, Long academicPeriodId, User user) {
        StudentProfile newStudentProfile = new StudentProfile();
        List<IntegraStudent> integraStudents = integraServicePort.getIntegraStudentByIdentification(identification);
        newStudentProfile.setSemester(
                integraStudents.stream()
                        .map(student -> {
                            String semesterStr = student.getSemester();
                            return (semesterStr == null || semesterStr.isEmpty()) ? 0 : Byte.parseByte(semesterStr);
                        })
                        .max(Byte::compare)
                        .orElse((byte) 0)
        );
        newStudentProfile.setAcademicPeriodId(academicPeriodId); // Assign the correct academic period
        newStudentProfile.setUserId(user.getId()); // Associate the created user
        newStudentProfile.setAcademicProgramsIds(academicProgramServicePort.findAcademicProgramsByAcademicProgramCodes(
                        integraStudents.stream()
                                .map(IntegraStudent::getProgramCode)
                                .collect(Collectors.toSet()))
                .stream()
                .map(AcademicProgram::getId)
                .collect(Collectors.toSet()));

        return studentProfileServicePort.save(newStudentProfile);
    }

    @Override
    public boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId) {
        return researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(
                studentProfileId, researchSeedbedProfileId
        );
    }

    private List<StudentProfile> findByAcademicPeriodId(Long academicPeriodId) {
        return studentProfileServicePort.findAllByAcademicPeriodId(academicPeriodId);
    }

    // Notice that this method suppose that the field with the identifications is called "identification"
    // Also, if some
    private List<User> getUserListByListOfMaps(List<Map<String, String>> cleanedStudentListOfMaps) {
        return cleanedStudentListOfMaps.stream()
                .map(studentProfile -> {
                    String identification = studentProfile.get(IDENTIFICATION); // Getting the identification from the Map
                    return userServicePort.findByUserIdentification(identification)
                            .orElseGet(() -> {
                                // If the user doesn't exist, we create it
                                IntegraStudent integraStudent = getFirstIntegraStudentFound(identification);
                                User user = getUserByIntegraStudent(integraStudent);
                                return userServicePort.save(user);
                            });
                })
                .toList();
    }

    private IntegraStudent getFirstIntegraStudentFound(String identification) {
        return integraServicePort.getIntegraStudentByIdentification(identification)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IntegraStudentNotFoundException(
                        String.format("IntegraStudent with identification %s not found", identification)
                ));
    }

    // This method filters out the student profiles that have empty values and checks if the students exist in Integra
    private List<Map<String, String>> getCleanedStudentListOfMaps(List<Map<String, String>> researchSeedbedStudentProfiles) {
        List<Map<String, String>> cleanedStudentListOfMaps = researchSeedbedStudentProfiles.stream()
                .filter(map -> map.values().stream().noneMatch(String::isEmpty))
                .toList();

        List<String> studentIdentifications = cleanedStudentListOfMaps.stream()
                .map(studentProfile -> studentProfile.get(IDENTIFICATION))
                .toList();

        List<String> missingIdentifications = integraServicePort
                .findMissingStudentIdentificationsInIntegra(studentIdentifications);

        if (!missingIdentifications.isEmpty()) {
            throw new IntegraStudentNotFoundException(
                    String.format(
                            "The following student identifications were not found in Integra: %s",
                            String.join(", ", missingIdentifications)
                    )
            );
        }
        return cleanedStudentListOfMaps;
    }

    private User getUserByIntegraStudent(IntegraStudent integraStudent) {
        User user = new User();
        user.setIdentificationNumber(integraStudent.getIdentification());
        user.setFullName(integraStudent.getName());
        user.setEmail(integraStudent.getEmail());
        user.setUserCode(integraStudent.getCodeStudent());
        user.setExternalUser(false);
        user.setSex(integraStudent.getSexo().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO);
        user.setRoleIds(Set.of(1L));
        return user;
    }

    private List<StudentProfile> getOrCreateStudentProfiles(List<Map<String, String>> cleanedStudentListOfMaps,
                                                            List<User> users, Long academicPeriodId) {
        List<StudentProfile> existingStudentProfiles = findByAcademicPeriodId(academicPeriodId);

        List<StudentProfile> newStudentProfiles = cleanedStudentListOfMaps.stream()
                .map(studentProfileMap -> getOrCreateStudentProfile(studentProfileMap, users, academicPeriodId))
                .toList();

        List<StudentProfile> allStudentProfiles = new ArrayList<>(existingStudentProfiles);
        allStudentProfiles.addAll(newStudentProfiles);
        return allStudentProfiles;
    }

    private StudentProfile getOrCreateStudentProfile(Map<String, String> studentProfileMap,
                                                     List<User> users, Long academicPeriodId) {
        String identification = studentProfileMap.get(IDENTIFICATION);
        User user = findUserByIdentification(users, identification);

        return studentProfileServicePort.findByUserIdAndAcademicPeriodId(user.getId(), academicPeriodId)
                .orElseGet(() -> createStudentProfileFromIntegraData(identification, academicPeriodId, user));
    }

    private User findUserByIdentification(List<User> users, String identification) {
        return users.stream()
                .filter(u -> u.getIdentificationNumber().equals(identification))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with identification %s not found", identification)
                ));
    }

    private List<ResearchSeedbedStudentProfile> createResearchSeedbedStudentProfiles(
            List<StudentProfile> allStudentProfiles, Long researchSeedbedProfileId) {
        return allStudentProfiles.stream()
                .filter(studentProfile -> !existsByStudentProfileIdAndResearchSeedbedProfileId(
                        studentProfile.getId(), researchSeedbedProfileId
                ))
                .map(studentProfile -> getResearchSeedbedStudentProfile(researchSeedbedProfileId, studentProfile.getId()))
                .toList();
    }
}
