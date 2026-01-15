package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing research seedbed student profile data.
 * <p>
 * This interface defines the contract for persisting and retrieving student participations
 * in research seedbeds. It represents the many-to-many relationship between student profiles
 * and research seedbed profiles, tracking which students participate in which seedbeds.
 * </p>
 */
public interface IResearchSeedbedStudentProfilePersistencePort {

    Optional<ResearchSeedbedStudentProfile> findById(Long id);
    ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfile> findAll();

    /**
     * Checks if a student is already enrolled in a specific research seedbed.
     *
     * @param studentProfileId         the unique identifier of the student profile
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @return {@code true} if the student is enrolled in the seedbed, {@code false} otherwise
     */
    boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId);

    /**
     * Retrieves all student participations for a specific research seedbed.
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @return a list of student profiles enrolled in the seedbed
     */
    List<ResearchSeedbedStudentProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);

    /**
     * Retrieves all seedbed participations for a specific student in a given academic period.
     *
     * @param studentProfileId the unique identifier of the student profile
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of seedbed participations for the student in the specified period
     */
    List<ResearchSeedbedStudentProfile> findAllByStudentProfileIdAndAcademicPeriodId(Long studentProfileId, Long academicPeriodId);
}
