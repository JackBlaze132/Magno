package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileSummaryResponse;
import com.unibague.magno.application.handler.impl.ResearchSeedbedStudentProfileHandler;
import com.unibague.magno.infrastructure.util.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/research-seedbed-student-profile")
@RequiredArgsConstructor
public class ResearchSeedbedStudentProfileRestController {

    private final ResearchSeedbedStudentProfileHandler researchSeedbedStudentProfileHandler;
    private final UploadService uploadService;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedStudentProfileResponse> getResearchSeedbedStudentProfileById(@PathVariable Long id) {
        ResearchSeedbedStudentProfileResponse response = researchSeedbedStudentProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedStudentProfileResponse>> getAllResearchSeedbedStudentProfiles() {
        List<ResearchSeedbedStudentProfileResponse> responses = researchSeedbedStudentProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedStudentProfileResponse> createResearchSeedbedStudentProfile
            (@RequestBody ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        ResearchSeedbedStudentProfileResponse created = researchSeedbedStudentProfileHandler.save(researchSeedbedStudentProfileRequest);
        URI location = URI.create("/api/research-seedbed-student-profile/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping(path = "/add-all-by-excel/{researchSeedbedProfileId}", headers = "API-VERSION=1")
    ResponseEntity<List<ResearchSeedbedStudentProfileSummaryResponse>> addAllResearchSeedbedStudentProfileByExcel(
            @PathVariable Long researchSeedbedProfileId, @RequestParam("file") MultipartFile file) {
        List<ResearchSeedbedStudentProfileSummaryResponse> responses = researchSeedbedStudentProfileHandler
                .saveAllByExcel(researchSeedbedProfileId, file);
        URI location = URI.create("/api/research-seedbed-student-profile/");
        return ResponseEntity.created(location).body(responses);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedStudentProfileResponse> updateResearchSeedbedStudentProfileById
            (@PathVariable Long id, @RequestBody ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        ResearchSeedbedStudentProfileResponse updated = researchSeedbedStudentProfileHandler.updateById(id, researchSeedbedStudentProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteResearchSeedbedStudentProfileById(@PathVariable Long id) {
        researchSeedbedStudentProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
