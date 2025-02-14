package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.mapper.request.ResearchSeedbedStudentProfileRequestMapper;
import com.unibague.magno.application.mapper.response.ResearchSeedbedStudentProfileResponseMapper;
import com.unibague.magno.domain.api.IResearchSeedbedStudentProfileServicePort;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchSeedbedStudentProfileHandler implements IResearchSeedbedStudentProfileHandler{

    private final IResearchSeedbedStudentProfileServicePort researchSeedbedStudentProfileServicePort;
    private final ResearchSeedbedStudentProfileRequestMapper researchSeedbedStudentProfileRequestMapper;
    private final ResearchSeedbedStudentProfileResponseMapper researchSeedbedStudentProfileResponseMapper;

    @Override
    public ResearchSeedbedStudentProfileResponse findById(Long id) {
        ResearchSeedbedStudentProfile researchSeedbedStudentProfile =researchSeedbedStudentProfileServicePort.findById(id);
        return researchSeedbedStudentProfileResponseMapper.toResponse(researchSeedbedStudentProfile);
    }

    @Override
    public ResearchSeedbedStudentProfileResponse save(ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        return researchSeedbedStudentProfileResponseMapper.toResponse(researchSeedbedStudentProfileServicePort
                .save(researchSeedbedStudentProfileRequestMapper
                        .toResearchSeedbedStudentProfile(researchSeedbedStudentProfileRequest)));
    }

    @Override
    public ResearchSeedbedStudentProfileResponse updateById(Long id, ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        return researchSeedbedStudentProfileResponseMapper.toResponse(researchSeedbedStudentProfileServicePort
                .update(id, researchSeedbedStudentProfileRequestMapper
                        .toResearchSeedbedStudentProfile(researchSeedbedStudentProfileRequest)));
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedStudentProfileServicePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedStudentProfileResponse> findAll() {
        return researchSeedbedStudentProfileResponseMapper.toResponseList(researchSeedbedStudentProfileServicePort.findAll());
    }
}
