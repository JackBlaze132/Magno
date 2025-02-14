package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResearchSeedbedStudentProfileRequestMapper {
    ResearchSeedbedStudentProfile toResearchSeedbedStudentProfile(
            ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest);
}
