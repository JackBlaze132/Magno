package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ResearchSeedbedRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;

import java.util.List;

public interface IResearchSeedbedHandler {
    ResearchSeedbedResponse findById(Long id);
    ResearchSeedbedResponse save(ResearchSeedbedRequest researchSeedbedRequest);
    ResearchSeedbedResponse updateById(Long id, ResearchSeedbedRequest researchSeedbedRequest);
    void deleteById(Long id);
    List<ResearchSeedbedResponse> findAll();
    List<ResearchSeedbedResponse> findResearchSeedbedsByUserId(Long userId);
}
