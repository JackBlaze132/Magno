package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;

import java.util.List;

public interface IResearchSeedbedStudentProfileHandler {
    ResearchSeedbedStudentProfileResponse findById(Long id);
    ResearchSeedbedStudentProfileResponse save(ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
    ResearchSeedbedStudentProfileResponse updateById(Long id, ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfileResponse> findAll();

}
