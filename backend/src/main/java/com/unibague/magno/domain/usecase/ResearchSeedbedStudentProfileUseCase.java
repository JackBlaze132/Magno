package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ResearchSeedbedStudentProfileNotFoundException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.spi.IResearchSeedbedStudentProfilePersistencePort;

import java.util.*;

public class ResearchSeedbedStudentProfileUseCase implements IResearchSeedbedStudentProfileServicePort {

    private final IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort;
    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IStudentProfileServicePort studentProfileServicePort;
    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;

    public static final String IDENTIFICATION = "identification";

    public ResearchSeedbedStudentProfileUseCase(
            IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort,
            IUserServicePort userServicePort,
            IIntegraServicePort integraServicePort,
            IStudentProfileServicePort studentProfileServicePort,
            IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort) {
        this.researchSeedbedStudentProfilePersistencePort = researchSeedbedStudentProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.integraServicePort = integraServicePort;
        this.studentProfileServicePort = studentProfileServicePort;
        this.researchSeedbedProfileServicePort = researchSeedbedProfileServicePort;
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
        if (researchSeedbedStudentProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedStudentProfileNotFoundException(
                    String.format("ResearchSeedbedStudentProfile with id %d could not be updated because it was not found", id));
        }
        return researchSeedbedStudentProfilePersistencePort.update(id, researchSeedbedStudentProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedStudentProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedStudentProfileNotFoundException(
                    String.format("ResearchSeedbedStudentProfile with id %d could not be deleted because it was not found", id));
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

        Long academicPeriodId = researchSeedbedProfileServicePort.findById(researchSeedbedProfileId).getAcademicPeriodId();

        // Clean the list because some maps can have empty values
        List<Map<String, String>> cleanedStudentListOfMaps = integraServicePort
                .getCleanedStudentListOfMaps(researchSeedbedStudentProfiles);

        // Verify and register users if they don't exist
        List<User> users = userServicePort.getUserListByListOfStudentMaps(cleanedStudentListOfMaps);

        // Get or create the student profiles if they don't exist
        List<StudentProfile> allStudentProfiles = studentProfileServicePort
                .getOrCreateStudentProfiles(cleanedStudentListOfMaps, users, academicPeriodId);

        // Create and return the research seedbed profiles
        return createResearchSeedbedStudentProfiles(allStudentProfiles, researchSeedbedProfileId);
    }

    @Override
    public boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId) {
        return researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(
                studentProfileId, researchSeedbedProfileId
        );
    }

    @Override
    public List<ResearchSeedbedStudentProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId) {
        return researchSeedbedStudentProfilePersistencePort.findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
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

    private ResearchSeedbedStudentProfile getResearchSeedbedStudentProfile(
            Long researchSeedbedProfileId, Long studentProfileId) {
        ResearchSeedbedStudentProfile researchSeedbedStudentProfile = new ResearchSeedbedStudentProfile();
        researchSeedbedStudentProfile.setStudentProfileId(studentProfileId);
        researchSeedbedStudentProfile.setResearchSeedbedProfileId(researchSeedbedProfileId);
        researchSeedbedStudentProfile.setWasActive(false);
        researchSeedbedStudentProfile.setIsLeader(false);
        return researchSeedbedStudentProfilePersistencePort.save(researchSeedbedStudentProfile);
    }
}
