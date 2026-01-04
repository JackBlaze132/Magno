package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;
import com.unibague.magno.application.handler.impl.ResearchSeedbedHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/research-seedbeds")
@RequiredArgsConstructor
public class ResearchSeedbedRestController {

    private final ResearchSeedbedHandler researchSeedbedHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedResponse> getResearchSeedbedById(@PathVariable Long id) {
        ResearchSeedbedResponse response = researchSeedbedHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    // No role restriction for this endpoint due to internal logic
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedResponse>> getAllResearchSeedbeds() {
        List<ResearchSeedbedResponse> responses = researchSeedbedHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).ESTUDIANTE)")
    @GetMapping(path = "/seedbeds-by-user-id/{userId}", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedResponse>> findResearchSeedbedsByUserId(@PathVariable Long userId) {
        List<ResearchSeedbedResponse> responses = researchSeedbedHandler.findResearchSeedbedsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedResponse> createResearchSeedbed
            (@Valid @RequestBody ResearchSeedbedRequest researchSeedbedRequest) {
        ResearchSeedbedResponse created = researchSeedbedHandler.save(researchSeedbedRequest);
        URI location = URI.create("/api/research-seedbeds/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedResponse> updateResearchSeedbedById
            (@PathVariable Long id, @Valid @RequestBody ResearchSeedbedRequest researchSeedbedRequest) {
        ResearchSeedbedResponse updated = researchSeedbedHandler.updateById(id, researchSeedbedRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteResearchSeedbedById(@PathVariable Long id) {
        researchSeedbedHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
