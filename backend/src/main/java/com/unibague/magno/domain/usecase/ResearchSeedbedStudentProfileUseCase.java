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

    private final static String IDENTIFICATION = "identification";

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

        // If some of the students aren't found in "integra", the process should be stopped
        List<IntegraStudent> integraStudentsList = researchSeedbedStudentProfiles.stream()
                .map(studentProfile -> {
                    String identification = studentProfile.get(IDENTIFICATION);
                    return integraServicePort.getIntegraStudentByIdentification(identification)
                            .stream().findFirst().orElseThrow(
                                    () -> new IntegraStudentNotFoundException(
                                            String.format("IntegraStudent with identification %s not found", identification)
                                    )
                            );
                }).toList();

        ResearchSeedbedProfile researchSeedbedProfile = researchSeedbedProfileServicePort.findById(researchSeedbedProfileId);
        Long academicPeriodId = researchSeedbedProfile.getAcademicPeriodId();


        // Verify and register users if they don't exist
        List<User> users = researchSeedbedStudentProfiles.stream()
                .map(studentProfile -> {
                    String identification = studentProfile.get("identification"); // Getting the identification from the Map
                    return userServicePort.findByUserIdentification(identification)
                            .orElseGet(() -> {
                                // If the user doesn't exist, we create it
                                IntegraStudent integraStudent = integraServicePort.getIntegraStudentByIdentification(identification)
                                        .stream()
                                        .findFirst()
                                        .orElseThrow(() -> new IntegraStudentNotFoundException(
                                                String.format("IntegraStudent with identification %s not found", identification)
                                        ));

                                User user = new User();
                                user.setIdentificationNumber(integraStudent.getIdentification());
                                user.setFullName(integraStudent.getName());
                                user.setEmail(integraStudent.getEmail());
                                user.setUserCode(integraStudent.getCodeStudent());
                                user.setExternalUser(false);
                                user.setSex(integraStudent.getSexo().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO);
                                user.setRoleIds(Set.of(1L));

                                return userServicePort.save(user);
                            });
                })
                .toList();

        List<StudentProfile> existingStudentProfiles = studentProfileServicePort.findAll()
                .stream()
                .filter(sp -> sp.getAcademicPeriodId().equals(academicPeriodId))
                .toList();

        // Create new StudentProfile only if they don't exist for the current academic period
        List<StudentProfile> newStudentProfiles = researchSeedbedStudentProfiles.stream()
                .map(studentProfile -> {
                    String identification = studentProfile.get(IDENTIFICATION);

                    // Search for the corresponding user in the list of created users
                    User user = users.stream()
                            .filter(u -> u.getIdentificationNumber().equals(identification))
                            .findFirst()
                            .orElseThrow(() -> new UserNotFoundException(
                                    String.format("User with identification %s not found", identification)
                            ));

                    // Verify if there is already a StudentProfile for this user and academic period
                    boolean profileExists = studentProfileServicePort.findAll()
                            .stream()
                            .anyMatch(sp -> sp.getUserId().equals(user.getId()) &&
                                    sp.getAcademicPeriodId().equals(academicPeriodId));

                    if (!profileExists) {
                        // If it doesn't exist, create a new StudentProfile
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
                    } else {
                        // If exits return it
                        return studentProfileServicePort.findByStudentProfileIdentificationAndResearchSeedbedProfileId(
                                identification, researchSeedbedProfileId
                        );
                    }
                })
                .toList();

        List<StudentProfile> allStudentProfiles = new ArrayList<>(existingStudentProfiles);
        allStudentProfiles.addAll(newStudentProfiles);


        return allStudentProfiles.stream()
                .map(studentProfile -> {
                    // Verify if there is already a record with the same studentProfileId and researchSeedbedProfileId
                    boolean exists = existsByStudentProfileIdAndResearchSeedbedProfileId(
                            studentProfile.getId(), researchSeedbedProfileId
                    );

                    if (!exists) {
                        // If it doesn't exist, create and save the new record
                        ResearchSeedbedStudentProfile researchSeedbedStudentProfile = new ResearchSeedbedStudentProfile();
                        researchSeedbedStudentProfile.setStudentProfileId(studentProfile.getId());
                        researchSeedbedStudentProfile.setResearchSeedbedProfileId(researchSeedbedProfileId);
                        researchSeedbedStudentProfile.setWasActive(false);
                        return researchSeedbedStudentProfilePersistencePort.save(researchSeedbedStudentProfile);
                    } else {
                        // If it exists, return null to filter it out
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId) {
        return researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(
                studentProfileId, researchSeedbedProfileId
        );
    }
}
