package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;

import java.util.List;

/**
 * Helper interface for research seedbed profile operations.
 * <p>
 * <strong>Why this helper exists:</strong> This helper is used to avoid circular dependencies
 * between use cases and to encapsulate the complex logic of managing functionary profiles
 * when coordinators or tutors change. The {@code ResearchSeedbedProfileUseCase} needs to
 * interact with FunctionaryProfile, Role, Dependency, and other services, which could create
 * circular dependency chains.
 * </p>
 * <p>
 * <strong>Responsibilities:</strong>
 * <ul>
 *   <li>Verify and create functionary profiles for coordinators and tutors</li>
 *   <li>Validate academic period status before operations</li>
 *   <li>Handle role reassignment when coordinators/tutors change</li>
 *   <li>Clean up orphaned functionary profiles when no longer needed</li>
 * </ul>
 * </p>
 */
public interface IResearchSeedbedProfileHelper {

    /**
     * Verifies that both coordinator and tutor have valid functionary profiles.
     * <p>
     * If a functionary profile doesn't exist for the coordinator or tutor in the
     * specified academic period, one is automatically created with the appropriate
     * role and dependency information from Integra.
     * </p>
     * <p>
     * Role assignment logic:
     * <ul>
     *   <li>Coordinator receives {@code COORDINADOR_DE_SEMILLERO} role</li>
     *   <li>Tutor receives {@code TUTOR_DE_SEMILLERO} role</li>
     *   <li>Existing profiles may have their roles upgraded if needed</li>
     * </ul>
     * </p>
     *
     * @param rsp the research seedbed profile containing coordinator, tutor, and period information
     * @return the updated research seedbed profile with functionary profile IDs set
     */
    ResearchSeedbedProfile verifyUsersHasFunctionaryProfiles(ResearchSeedbedProfile rsp);

    /**
     * Verifies that the academic period is currently active.
     *
     * @param academicPeriodId the ID of the academic period to verify
     * @param errorMessage     custom error message for the exception
     * @throws AcademicPeriodNotCurrentException if the academic period is not current
     */
    void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage);

    /**
     * Handles functionary profile changes when a seedbed profile is updated or deleted.
     * <p>
     * When coordinators or tutors change, this method determines what to do with
     * the old functionary profiles based on their usage across all seedbed profiles
     * in the academic period:
     * <ul>
     *   <li>If still an investigation group coordinator, keep their role</li>
     *   <li>If still a seedbed coordinator elsewhere, update to {@code COORDINADOR_DE_SEMILLERO}</li>
     *   <li>If only a tutor elsewhere, update to {@code TUTOR_DE_SEMILLERO}</li>
     *   <li>If not used anywhere, delete the functionary profile</li>
     * </ul>
     * </p>
     *
     * @param researchSeedbedProfiles current list of seedbed profiles in the academic period (after update/delete)
     * @param academicPeriodId        the ID of the academic period
     * @param oldCoordinatorId        the ID of the previous coordinator's functionary profile
     * @param oldTutorId              the ID of the previous tutor's functionary profile (can be null)
     */
    void handleFunctionaryProfileChangesOnUpdate(List<ResearchSeedbedProfile> researchSeedbedProfiles,
                                                  Long academicPeriodId, Long oldCoordinatorId, Long oldTutorId);
}
