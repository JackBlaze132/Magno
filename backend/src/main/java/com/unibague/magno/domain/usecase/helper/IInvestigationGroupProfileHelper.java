package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotVisibleException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileFunctionaryIsAlreadyACoordinatorException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileHasResearchSeedbedProfilesException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;

/**
 * Helper interface for investigation group profile operations.
 * <p>
 * <strong>Why this helper exists:</strong> This helper is used to avoid circular dependencies
 * between use cases. The {@code InvestigationGroupProfileUseCase} needs to interact with
 * multiple services (FunctionaryProfile, Role, User, etc.) that could create circular
 * dependency chains. By extracting these operations into a helper, we break these cycles
 * and keep the use case focused on its core responsibilities.
 * </p>
 * <p>
 * <strong>Responsibilities:</strong>
 * <ul>
 *   <li>Verify and create functionary profiles for coordinators</li>
 *   <li>Validate academic period status before operations</li>
 *   <li>Ensure uniqueness of coordinators per academic period</li>
 *   <li>Handle role changes when coordinators are reassigned</li>
 *   <li>Validate deletion constraints (no associated seedbed profiles)</li>
 * </ul>
 * </p>
 */
public interface IInvestigationGroupProfileHelper {

    /**
     * Verifies that the coordinator has a valid functionary profile for the academic period.
     * <p>
     * If the coordinator doesn't have a functionary profile in the specified academic period,
     * one is automatically created with the appropriate role and dependency information
     * from Integra.
     * </p>
     *
     * @param igp the investigation group profile containing coordinator and period information
     * @return the updated investigation group profile with the functionary profile ID set
     */
    InvestigationGroupProfile verifyUserHasFunctionaryProfile(InvestigationGroupProfile igp);

    /**
     * Verifies that the academic period is currently active.
     *
     * @param academicPeriodId the ID of the academic period to verify
     * @param errorMessage     custom error message for the exception
     * @throws AcademicPeriodNotCurrentException if the academic period is not current
     */
    void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage);

    /**
     * Verifies that the academic period is visible.
     * <p>
     * Non-visible academic periods are hidden from regular users and should not allow
     * creation of investigation group profiles.
     * </p>
     *
     * @param academicPeriodId the ID of the academic period to verify
     * @param errorMessage     custom error message for the exception
     * @throws AcademicPeriodNotVisibleException if the academic period is not visible
     */
    void verifyAcademicPeriodIsVisible(Long academicPeriodId, String errorMessage);

    /**
     * Verifies that a user is not already a coordinator of another investigation group
     * in the same academic period.
     *
     * @param coordinatorId    the user ID of the proposed coordinator
     * @param academicPeriodId the ID of the academic period
     * @throws InvestigationGroupProfileFunctionaryIsAlreadyACoordinatorException if the user is already a coordinator
     */
    void verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(Long coordinatorId, Long academicPeriodId);

    /**
     * Verifies that the investigation group profile has no associated research seedbed profiles.
     * <p>
     * This validation is required before deletion to maintain referential integrity.
     * </p>
     *
     * @param investigationGroupProfileId the ID of the investigation group profile to verify
     * @throws InvestigationGroupProfileHasResearchSeedbedProfilesException if seedbed profiles exist
     */
    void verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles(Long investigationGroupProfileId);

    /**
     * Handles functionary profile changes when updating an investigation group profile.
     * <p>
     * When the coordinator changes, the old coordinator's functionary profile may need to be:
     * <ul>
     *   <li>Updated to a lower role if they're still a seedbed coordinator/tutor</li>
     *   <li>Deleted if they're no longer associated with any seedbed or group</li>
     * </ul>
     * </p>
     *
     * @param oldCoordinatorId              the ID of the previous coordinator's functionary profile
     * @param academicPeriodId              the ID of the academic period
     * @param investigationGroupProfileId   the ID of the investigation group profile being updated
     */
    void handleFunctionaryProfileChangeOnUpdate(Long oldCoordinatorId, Long academicPeriodId, Long investigationGroupProfileId);
}
