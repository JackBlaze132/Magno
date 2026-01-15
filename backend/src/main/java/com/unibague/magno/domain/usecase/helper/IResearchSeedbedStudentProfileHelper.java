package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

/**
 * Helper interface for research seedbed student profile operations.
 * <p>
 * <strong>Why this helper exists:</strong> This helper is used to avoid circular dependencies
 * between use cases. The {@code ResearchSeedbedStudentProfileUseCase} needs to interact with
 * StudentProfile and AcademicPeriod services, which could create circular dependency chains.
 * The helper encapsulates the logic for verifying and creating student profiles.
 * </p>
 * <p>
 * <strong>Responsibilities:</strong>
 * <ul>
 *   <li>Verify that students have valid profiles in the academic period</li>
 *   <li>Automatically create student profiles from Integra data when needed</li>
 *   <li>Check academic period status for validation</li>
 * </ul>
 * </p>
 */
public interface IResearchSeedbedStudentProfileHelper {

    /**
     * Verifies that the student has a valid student profile for the academic period.
     * <p>
     * If the student doesn't have a profile in the academic period associated with
     * the research seedbed, one is automatically created using data from Integra.
     * </p>
     *
     * @param researchSeedbedStudentProfile the profile containing student and seedbed information
     * @return the updated profile with the correct student profile ID set
     */
    ResearchSeedbedStudentProfile verifyStudentHasAProfile(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);

    /**
     * Checks if the academic period is NOT in current status.
     * <p>
     * This is used to validate that operations can only be performed on active
     * academic periods.
     * </p>
     *
     * @param academicPeriodId the ID of the academic period to verify
     * @return {@code true} if the academic period is NOT current (operations should be blocked),
     *         {@code false} if the academic period IS current (operations are allowed)
     */
    boolean verifyAcademicPeriodIsCurrentStatus(Long academicPeriodId);
}
