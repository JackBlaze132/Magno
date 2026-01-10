package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ALeaderAlreadyExistsInSeedbedException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ResearchSeedbedStudentProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.StudentProfileAlreadyExistsInSeedbedException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IResearchSeedbedStudentProfilePersistencePort;
import com.unibague.magno.domain.usecase.helper.IResearchSeedbedStudentProfileHelper;

import java.util.*;

public class ResearchSeedbedStudentProfileUseCase implements IResearchSeedbedStudentProfileServicePort {

    private final IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort;
    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IStudentProfileServicePort studentProfileServicePort;
    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final IResearchSeedbedStudentProfileHelper researchSeedbedStudentProfileHelper;
    private final IRoleServicePort roleServicePort;

    public static final String IDENTIFICATION = "identification";

    public ResearchSeedbedStudentProfileUseCase(
            IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort,
            IUserServicePort userServicePort,
            IIntegraServicePort integraServicePort,
            IStudentProfileServicePort studentProfileServicePort,
            IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort,
            IResearchSeedbedStudentProfileHelper researchSeedbedStudentProfileHelper,
            IRoleServicePort roleServicePort) {
        this.researchSeedbedStudentProfilePersistencePort = researchSeedbedStudentProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.integraServicePort = integraServicePort;
        this.studentProfileServicePort = studentProfileServicePort;
        this.researchSeedbedProfileServicePort = researchSeedbedProfileServicePort;
        this.researchSeedbedStudentProfileHelper = researchSeedbedStudentProfileHelper;
        this.roleServicePort = roleServicePort;
    }

    @Override
    public ResearchSeedbedStudentProfile findById(Long id) {
        return researchSeedbedStudentProfilePersistencePort.findById(id)
                .orElseThrow(() -> new ResearchSeedbedStudentProfileNotFoundException(
                        String.format("Perfil de estudiante de semillero de investigación con ID %d no encontrado", id)));
    }

    @Override
    public ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        verifyAcademicPeriodIsCurrent(
                researchSeedbedStudentProfile.getResearchSeedbedProfileId(),
                "No se puede agregar un estudiante a un perfil de semillero de investigación asociado a un período académico inactivo."
        );

        ResearchSeedbedStudentProfile rssp = researchSeedbedStudentProfileHelper
                .verifyStudentHasAProfile(researchSeedbedStudentProfile);

        verifyStudentProfileAlreadyExistsInSeedbed(rssp.getStudentProfileId(), rssp.getResearchSeedbedProfileId());

        if (Boolean.TRUE.equals(rssp.getIsLeader())) {
            verifyAlreadyExistsALeader(rssp.getResearchSeedbedProfileId());
        }
        
        ResearchSeedbedStudentProfile savedProfile = researchSeedbedStudentProfilePersistencePort.save(rssp);
        
        // Assign the correct role to the student profile based on ALL their seedbed profiles
        ResearchSeedbedProfile rsp = getResearchSeedbedProfile(rssp.getResearchSeedbedProfileId());
        updateStudentProfileRole(rssp.getStudentProfileId(), rsp.getAcademicPeriodId());
        
        return savedProfile;
    }

    private void verifyStudentProfileAlreadyExistsInSeedbed(Long studentProfileId, Long researchSeedbedProfileId) {
        boolean exists = researchSeedbedStudentProfilePersistencePort
                .existsByStudentProfileIdAndResearchSeedbedProfileId(studentProfileId, researchSeedbedProfileId);
        if (exists) {
            throw new StudentProfileAlreadyExistsInSeedbedException(
                    String.format("El perfil de estudiante con ID %d ya está asociado al perfil de semillero de investigación con ID %d",
                            studentProfileId, researchSeedbedProfileId));
        }
    }

    private void verifyAlreadyExistsALeader(Long researchSeedbedProfileId) {
        List<ResearchSeedbedStudentProfile> leaders = researchSeedbedStudentProfilePersistencePort
                .findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
        boolean hasLeader = leaders.stream().anyMatch(ResearchSeedbedStudentProfile::getIsLeader);
        if (hasLeader) {
            throw new ALeaderAlreadyExistsInSeedbedException(
                    String.format("El perfil de semillero de investigación con ID %d ya tiene un líder asignado",
                            researchSeedbedProfileId));
        }
    }

    @Override
    public ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {
        researchSeedbedStudentProfile.setId(id);
        ResearchSeedbedStudentProfile existingProfile = researchSeedbedStudentProfilePersistencePort.findById(id)
                .orElseThrow(() -> new ResearchSeedbedStudentProfileNotFoundException(
                        String.format("No se pudo actualizar el perfil de estudiante de semillero de investigación con ID %d porque no fue encontrado", id)));

        verifyAcademicPeriodIsCurrent(
                researchSeedbedStudentProfile.getResearchSeedbedProfileId(),
                "No se puede actualizar un estudiante en un perfil de semillero de investigación asociado a un período académico inactivo."
        );

        if (Boolean.TRUE.equals(researchSeedbedStudentProfile.getIsLeader())){
            verifyIfWhenUpdatingTryingToAddMoreThanOneLeader(researchSeedbedStudentProfile);
        }
        
        ResearchSeedbedStudentProfile updatedProfile = researchSeedbedStudentProfilePersistencePort.update(id, researchSeedbedStudentProfile);
        
        // Update the role if isLeader status changed, considering ALL student's seedbed profiles
        if (!existingProfile.getIsLeader().equals(researchSeedbedStudentProfile.getIsLeader())) {
            ResearchSeedbedProfile rsp = getResearchSeedbedProfile(researchSeedbedStudentProfile.getResearchSeedbedProfileId());
            updateStudentProfileRole(researchSeedbedStudentProfile.getStudentProfileId(), 
                                   rsp.getAcademicPeriodId());
        }
        
        return updatedProfile;
    }

    private void verifyIfWhenUpdatingTryingToAddMoreThanOneLeader(ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {
        try {
            verifyAlreadyExistsALeader(researchSeedbedStudentProfile.getResearchSeedbedProfileId());
        } catch (ALeaderAlreadyExistsInSeedbedException e) {
            ResearchSeedbedStudentProfile existingLeader = researchSeedbedStudentProfilePersistencePort
                    .findAllByResearchSeedbedProfileId(researchSeedbedStudentProfile.getResearchSeedbedProfileId())
                    .stream()
                    .filter(ResearchSeedbedStudentProfile::getIsLeader)
                    .findFirst()
                    .orElse(null);

            if (existingLeader != null && !existingLeader.getId().equals(researchSeedbedStudentProfile.getId())) {
                throw e;
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedStudentProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedStudentProfileNotFoundException(
                    String.format("No se pudo eliminar el perfil de estudiante de semillero de investigación con ID %d porque no fue encontrado", id));
        }
        ResearchSeedbedStudentProfile rssp = findById(id);
        verifyAcademicPeriodIsCurrent(
                rssp.getResearchSeedbedProfileId(),
                "No se puede eliminar un estudiante de un perfil de semillero de investigación asociado a un período académico inactivo."
        );

        Long studentProfileId = rssp.getStudentProfileId();
        Long researchSeedbedProfileId = rssp.getResearchSeedbedProfileId();
        ResearchSeedbedProfile rsp = getResearchSeedbedProfile(researchSeedbedProfileId);
        Long academicPeriodId = rsp.getAcademicPeriodId();
        
        researchSeedbedStudentProfilePersistencePort.deleteById(id);
        
        // Check if student still has profiles in this academic period
        List<ResearchSeedbedStudentProfile> remainingProfiles = 
                findAllByStudentProfileIdAndAcademicPeriodId(studentProfileId, academicPeriodId);
        
        if (remainingProfiles.isEmpty()) {
            // No more profiles, delete the student profile
            deleteStudentProfileIfNoMoreRSSP(studentProfileId, academicPeriodId);
        } else {
            // Still has profiles, update the role based on remaining profiles
            updateStudentProfileRole(studentProfileId, academicPeriodId);
        }
    }

    private void deleteStudentProfileIfNoMoreRSSP(Long studentProfileId, Long academicPeriodId) {
        List<ResearchSeedbedStudentProfile> allByStudentProfileId =
                findAllByStudentProfileIdAndAcademicPeriodId(studentProfileId, academicPeriodId);
        if (allByStudentProfileId.isEmpty()) {
            studentProfileServicePort.deleteById(studentProfileId);
        }
    }

    @Override
    public List<ResearchSeedbedStudentProfile> findAll() {
        return researchSeedbedStudentProfilePersistencePort.findAll();
    }

    @Override
    public List<ResearchSeedbedStudentProfile> saveAllByExcel(Long researchSeedbedProfileId,
                                                              List<Map<String, String>> researchSeedbedStudentProfiles) {

        Long academicPeriodId = researchSeedbedProfileServicePort.findById(researchSeedbedProfileId).getAcademicPeriodId();

        verifyAcademicPeriodIsCurrent(
                researchSeedbedProfileId,
                "No se pueden agregar estudiantes a un perfil de semillero de investigación asociado a un período académico inactivo."
        );

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

    @Override
    public List<ResearchSeedbedStudentProfile> findAllByStudentProfileIdAndAcademicPeriodId(Long studentProfileId, Long academicPeriodId) {
        return researchSeedbedStudentProfilePersistencePort
                .findAllByStudentProfileIdAndAcademicPeriodId(studentProfileId, academicPeriodId);
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

    /**
     * Verifies that the academic period associated with the research seedbed profile
     * is in current status. Throws an exception otherwise.
     *
     * @param researchSeedbedProfileId The ID of the research seedbed profile
     * @param errorMessage Custom error message for the exception
     * @throws AcademicPeriodNotCurrentException if the academic period is not current
     */
    private void verifyAcademicPeriodIsCurrent(Long researchSeedbedProfileId, String errorMessage) {
        ResearchSeedbedProfile rsp = getResearchSeedbedProfile(researchSeedbedProfileId);

        boolean isNotCurrent =
                researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(rsp.getAcademicPeriodId());

        if (isNotCurrent) {
            throw new AcademicPeriodNotCurrentException(errorMessage);
        }
    }

    private ResearchSeedbedProfile getResearchSeedbedProfile(Long researchSeedbedProfileId) {
        return researchSeedbedProfileServicePort.findById(researchSeedbedProfileId);
    }

    /**
     * Updates the student profile with the appropriate role based on ALL their seedbed profiles
     * in the current academic period. Assigns ESTUDIANTE_LIDER if the student is a leader in
     * at least one seedbed, otherwise assigns ESTUDIANTE.
     *
     * @param studentProfileId The ID of the student profile to update
     * @param academicPeriodId The ID of the academic period
     */
    private void updateStudentProfileRole(Long studentProfileId, Long academicPeriodId) {
        // Get all research seedbed student profiles for this student in the academic period
        List<ResearchSeedbedStudentProfile> allStudentProfiles = 
                findAllByStudentProfileIdAndAcademicPeriodId(studentProfileId, academicPeriodId);
        
        // Check if the student is a leader in at least one seedbed
        boolean isLeaderInAnySeedbed = allStudentProfiles.stream()
                .anyMatch(profile -> Boolean.TRUE.equals(profile.getIsLeader()));
        
        SeedbedRole targetRole = isLeaderInAnySeedbed 
                ? SeedbedRole.ESTUDIANTE_LIDER 
                : SeedbedRole.ESTUDIANTE;
        
        Role role = roleServicePort.findByName(targetRole);
        
        // Update only the roleId field without affecting related entities (prevents orphanRemoval issues)
        studentProfileServicePort.updateRoleId(studentProfileId, role.getId());
    }

}
