package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.ResearchSeedbedRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;
import com.unibague.magno.application.mapper.request.ResearchSeedbedRequestMapper;
import com.unibague.magno.application.mapper.response.ResearchSeedbedResponseMapper;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.model.ResearchSeedbed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchSeedbedHandler implements IResearchSeedbedHandler{

    private final IResearchSeedbedServicePort researchSeedbedServicePort;
    private final ResearchSeedbedRequestMapper researchSeedbedRequestMapper;
    private final ResearchSeedbedResponseMapper researchSeedbedResponseMapper;

    @Override
    public ResearchSeedbedResponse findById(Long id) {
        ResearchSeedbed researchSeedbed = researchSeedbedServicePort.findById(id);
        return researchSeedbedResponseMapper.toResponse(researchSeedbed);
    }

    @Override
    public ResearchSeedbedResponse save(ResearchSeedbedRequest researchSeedbedRequest) {
        return researchSeedbedResponseMapper.toResponse(researchSeedbedServicePort
                .save(researchSeedbedRequestMapper.toResearchSeedbed(researchSeedbedRequest)));
    }

    @Override
    public ResearchSeedbedResponse updateById(Long id, ResearchSeedbedRequest researchSeedbedRequest) {
        return researchSeedbedResponseMapper.toResponse(researchSeedbedServicePort
                .update(id, researchSeedbedRequestMapper.toResearchSeedbed(researchSeedbedRequest)));
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedServicePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedResponse> findAll() {
        return researchSeedbedResponseMapper.toResponseList(researchSeedbedServicePort.findAll());
    }
}
