package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;
import java.util.Map;

/**
 * Service port interface that defines the contract for research seedbed student profile management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to research seedbed student profiles,
 * which represent the relationship between students and research seedbeds, including their participation
 * status and roles within the seedbed.
 * </p>
 *
 * @see ResearchSeedbedStudentProfile
 */
public interface IResearchSeedbedStudentProfileServicePort {
    
    /**
     * Retrieves a research seedbed student profile by its unique identifier.
     *
     * @param id the unique identifier of the research seedbed student profile
     * @return the research seedbed student profile with the specified ID
     */
    ResearchSeedbedStudentProfile findById(Long id);
    
    /**
     * Persists a new research seedbed student profile.
     *
     * @param researchSeedbedStudentProfile the research seedbed student profile to save
     * @return the saved research seedbed student profile
     */
    ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    
    /**
     * Updates an existing research seedbed student profile.
     *
     * @param id the unique identifier of the research seedbed student profile to update
     * @param researchSeedbedStudentProfile the research seedbed student profile data to update
     * @return the updated research seedbed student profile
     */
    ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    
    /**
     * Deletes a research seedbed student profile by its unique identifier.
     *
     * @param id the unique identifier of the research seedbed student profile to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all research seedbed student profiles in the system.
     *
     * @return a list of all research seedbed student profiles
     */
    List<ResearchSeedbedStudentProfile> findAll();
    
    /**
     * Bulk saves research seedbed student profiles from Excel data.
     * <p>
     * This method processes a list of student data from an Excel file and creates
     * corresponding research seedbed student profile entries.
     * </p>
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @param researchSeedbedStudentProfiles a list of maps containing student profile data
     * @return a list of saved research seedbed student profiles
     */
    List<ResearchSeedbedStudentProfile> saveAllByExcel(Long researchSeedbedProfileId,
                                                       List<Map<String, String>> researchSeedbedStudentProfiles);
    
    /**
     * Checks if a research seedbed student profile exists for a specific student and seedbed profile.
     *
     * @param studentProfileId the unique identifier of the student profile
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @return {@code true} if a profile exists for the student and seedbed, {@code false} otherwise
     */
    boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId);

    /**
     * Retrieves all student profiles associated with a specific research seedbed profile.
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @return a list of research seedbed student profiles for the specified seedbed profile
     */
    List<ResearchSeedbedStudentProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
    
    /**
     * Retrieves all research seedbed participations for a specific student in an academic period.
     *
     * @param studentProfileId the unique identifier of the student profile
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of research seedbed student profiles for the specified student and period
     */
    List<ResearchSeedbedStudentProfile> findAllByStudentProfileIdAndAcademicPeriodId(Long studentProfileId, Long academicPeriodId);
}
