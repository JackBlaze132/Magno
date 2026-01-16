package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileSummaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Handler interface for research seedbed student profile operations.
 * Manages student memberships in research seedbeds including bulk import
 * capabilities via Excel files.
 */
public interface IResearchSeedbedStudentProfileHandler {
    ResearchSeedbedStudentProfileResponse findById(Long id);
    ResearchSeedbedStudentProfileResponse save(ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
    ResearchSeedbedStudentProfileResponse updateById(Long id, ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfileResponse> findAll();

    /**
     * Bulk imports student memberships from an Excel file.
     * Creates student profiles and enrollments for all students in the file.
     *
     * @param researchSeedbedProfileId the target research seedbed profile
     * @param file the Excel file containing student data
     * @return list of created student profile summaries
     */
    List<ResearchSeedbedStudentProfileSummaryResponse> saveAllByExcel(Long researchSeedbedProfileId, MultipartFile file);

    /**
     * Retrieves all student memberships for a specific research seedbed profile.
     *
     * @param researchSeedbedProfileId the research seedbed profile identifier
     * @return list of student profiles in the specified seedbed
     */
    List<ResearchSeedbedStudentProfileResponse> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
