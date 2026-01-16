package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ResearchSeedbedRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;

import java.util.List;

/**
 * Handler interface for research seedbed operations.
 * Acts as the application layer bridge between REST controllers and domain services,
 * handling DTO-to-model conversion for research seedbed management.
 */
public interface IResearchSeedbedHandler {
    ResearchSeedbedResponse findById(Long id);
    ResearchSeedbedResponse save(ResearchSeedbedRequest researchSeedbedRequest);
    ResearchSeedbedResponse updateById(Long id, ResearchSeedbedRequest researchSeedbedRequest);
    void deleteById(Long id);
    List<ResearchSeedbedResponse> findAll();

    /**
     * Retrieves all research seedbeds associated with a specific user.
     * Includes seedbeds where the user participates as student, coordinator, or tutor.
     *
     * @param userId the user identifier
     * @return list of research seedbeds associated with the user
     */
    List<ResearchSeedbedResponse> findResearchSeedbedsByUserId(Long userId);
}
