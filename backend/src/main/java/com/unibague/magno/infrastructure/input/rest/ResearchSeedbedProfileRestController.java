package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.handler.ResearchSeedbedProfileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/research-seedbed-profiles")
public class ResearchSeedbedProfileRestController {

    private final ResearchSeedbedProfileHandler researchSeedbedProfileHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedProfileResponse> getResearchSeedbedProfileById(@PathVariable Long id) {
        ResearchSeedbedProfileResponse response = researchSeedbedProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedProfileResponse>> getAllResearchSeedbedProfiles() {
        List<ResearchSeedbedProfileResponse> responses = researchSeedbedProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedProfileResponse> createResearchSeedbedProfile
            (@RequestBody ResearchSeedbedProfileRequest researchSeedbedProfileRequest) {
        ResearchSeedbedProfileResponse created = researchSeedbedProfileHandler.save(researchSeedbedProfileRequest);
        URI location = URI.create(String.format("/api/research-seedbed-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedProfileResponse> updateResearchSeedbedProfileById
            (@PathVariable Long id, @RequestBody ResearchSeedbedProfileRequest researchSeedbedProfileRequest) {
        ResearchSeedbedProfileResponse updated = researchSeedbedProfileHandler.updateById(id, researchSeedbedProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteResearchSeedbedProfileById(@PathVariable Long id) {
        researchSeedbedProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
