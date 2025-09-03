package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileSummaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IResearchSeedbedStudentProfileHandler {
    ResearchSeedbedStudentProfileResponse findById(Long id);
    ResearchSeedbedStudentProfileResponse save(ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
    ResearchSeedbedStudentProfileResponse updateById(Long id, ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfileResponse> findAll();
    List<ResearchSeedbedStudentProfileSummaryResponse> saveAllByExcel(Long researchSeedbedProfileId, MultipartFile file);
    List<ResearchSeedbedStudentProfileResponse> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
