package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;
import com.unibague.magno.application.handler.ResearchSeedbedHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/research-seedbeds")
@RequiredArgsConstructor
public class ResearchSeedbedRestController {

    private final ResearchSeedbedHandler researchSeedbedHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedResponse> getResearchSeedbedById(@PathVariable Long id) {
        ResearchSeedbedResponse response = researchSeedbedHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedResponse>> getAllResearchSeedbeds() {
        List<ResearchSeedbedResponse> responses = researchSeedbedHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedResponse> createResearchSeedbed
            (@Valid @RequestBody ResearchSeedbedRequest researchSeedbedRequest) {
        ResearchSeedbedResponse created = researchSeedbedHandler.save(researchSeedbedRequest);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedResponse> updateResearchSeedbedById
            (@PathVariable Long id, @Valid @RequestBody ResearchSeedbedRequest researchSeedbedRequest) {
        ResearchSeedbedResponse updated = researchSeedbedHandler.updateById(id, researchSeedbedRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteResearchSeedbedById(@PathVariable Long id) {
        researchSeedbedHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
