package com.unibague.magno.infrastructure.configuration.security;

import com.unibague.magno.application.dto.util.CurrentUserInfo;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileNotFoundException;
import com.unibague.magno.domain.exception.security.NotAllowedToDoThisActionException;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.repository.IResearchSeedbedProfileRepository;
import com.unibague.magno.infrastructure.output.jpa.repository.IResearchSeedbedStudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling authorization checks related to research seedbed profiles.
 * Validates whether the current user has permission to perform actions on specific
 * research seedbed profiles based on their role or coordinator status.
 */
@Service
@RequiredArgsConstructor
public class ResearchSeedbedProfileAuthorizationService {

    private final IResearchSeedbedProfileRepository researchSeedbedProfileRepository;
    private final IResearchSeedbedStudentProfileRepository researchSeedbedStudentProfileRepository;

    /**
     * Validates that the current user can modify the specified research seedbed profile.
     * Authorization is granted if the user:
     * - Has DIRI role (global admin)
     * - Is the coordinator of the research seedbed profile
     * - Is the coordinator of the investigation group that contains the seedbed
     *
     * @param researchSeedbedProfileId the ID of the research seedbed profile
     * @param currentUserInfo the current authenticated user info
     * @throws NotAllowedToDoThisActionException if the user is not authorized
     * @throws ResearchSeedbedProfileNotFoundException if the profile does not exist
     */
    public void validateCanModifyResearchSeedbedProfile(Long researchSeedbedProfileId, CurrentUserInfo currentUserInfo) {
        if (hasDiriRole(currentUserInfo)) {
            return;
        }

        ResearchSeedbedProfileEntity profile = researchSeedbedProfileRepository.findById(researchSeedbedProfileId)
                .orElseThrow(() -> new ResearchSeedbedProfileNotFoundException(
                        String.format("Perfil de semillero con ID %d no encontrado", researchSeedbedProfileId)));

        if (isResearchSeedbedCoordinator(profile, currentUserInfo)) {
            return;
        }

        if (isInvestigationGroupCoordinator(profile, currentUserInfo)) {
            return;
        }

        throw new NotAllowedToDoThisActionException(
                "No tienes permisos para modificar este perfil de semillero. " +
                "Solo el coordinador del semillero, el coordinador del grupo de investigación o DIRI pueden hacerlo.");
    }

    /**
     * Validates that the current user can modify a research seedbed student profile.
     * Authorization is granted if the user:
     * - Has DIRI role (global admin)
     * - Is the coordinator of the research seedbed profile
     * - Is the coordinator of the investigation group that contains the seedbed
     *
     * @param researchSeedbedStudentProfileId the ID of the student profile
     * @param currentUserInfo the current authenticated user info
     * @throws NotAllowedToDoThisActionException if the user is not authorized
     */
    public void validateCanModifyResearchSeedbedStudentProfile(Long researchSeedbedStudentProfileId, CurrentUserInfo currentUserInfo) {
        if (hasDiriRole(currentUserInfo)) {
            return;
        }

        ResearchSeedbedStudentProfileEntity studentProfile = researchSeedbedStudentProfileRepository
                .findById(researchSeedbedStudentProfileId)
                .orElseThrow(() -> new NotAllowedToDoThisActionException(
                        String.format("Perfil de estudiante de semillero con ID %d no encontrado", researchSeedbedStudentProfileId)));

        ResearchSeedbedProfileEntity profile = studentProfile.getResearchSeedbedProfile();

        if (isResearchSeedbedCoordinator(profile, currentUserInfo)) {
            return;
        }

        if (isInvestigationGroupCoordinator(profile, currentUserInfo)) {
            return;
        }

        throw new NotAllowedToDoThisActionException(
                "No tienes permisos para modificar este perfil de estudiante. " +
                "Solo el coordinador del semillero, el coordinador del grupo de investigación o DIRI pueden hacerlo.");
    }

    /**
     * Validates that the current user can add students to the specified research seedbed profile.
     * Uses the same authorization rules as modifying the profile.
     *
     * @param researchSeedbedProfileId the ID of the research seedbed profile
     * @param currentUserInfo the current authenticated user info
     * @throws NotAllowedToDoThisActionException if the user is not authorized
     */
    public void validateCanAddStudentsToResearchSeedbedProfile(Long researchSeedbedProfileId, CurrentUserInfo currentUserInfo) {
        validateCanModifyResearchSeedbedProfile(researchSeedbedProfileId, currentUserInfo);
    }

    /**
     * Checks if the current user has DIRI role.
     */
    private boolean hasDiriRole(CurrentUserInfo currentUserInfo) {
        return currentUserInfo.getRoles().contains(SeedbedRole.DIRI.getAuthority());
    }

    /**
     * Checks if the current user is the coordinator of the research seedbed profile.
     */
    private boolean isResearchSeedbedCoordinator(ResearchSeedbedProfileEntity profile, CurrentUserInfo currentUserInfo) {
        if (profile.getCoordinator() == null || profile.getCoordinator().getUser() == null) {
            return false;
        }
        Long coordinatorUserId = profile.getCoordinator().getUser().getId();
        return coordinatorUserId.equals(currentUserInfo.getUserId());
    }

    /**
     * Checks if the current user is the coordinator of the investigation group
     * that contains the research seedbed profile.
     */
    private boolean isInvestigationGroupCoordinator(ResearchSeedbedProfileEntity profile, CurrentUserInfo currentUserInfo) {
        if (profile.getInvestigationGroupProfile() == null ||
            profile.getInvestigationGroupProfile().getCoordinator() == null ||
            profile.getInvestigationGroupProfile().getCoordinator().getUser() == null) {
            return false;
        }
        Long igCoordinatorUserId = profile.getInvestigationGroupProfile().getCoordinator().getUser().getId();
        return igCoordinatorUserId.equals(currentUserInfo.getUserId());
    }
}

