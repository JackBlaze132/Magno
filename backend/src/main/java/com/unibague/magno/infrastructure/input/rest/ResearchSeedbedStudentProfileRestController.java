package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileSummaryResponse;
import com.unibague.magno.application.handler.impl.ResearchSeedbedStudentProfileHandler;
import com.unibague.magno.infrastructure.util.excel.UploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/research-seedbed-student-profile")
@RequiredArgsConstructor
public class ResearchSeedbedStudentProfileRestController {

    private final ResearchSeedbedStudentProfileHandler researchSeedbedStudentProfileHandler;
    private final UploadService uploadService;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedStudentProfileResponse> getResearchSeedbedStudentProfileById(@PathVariable Long id) {
        ResearchSeedbedStudentProfileResponse response = researchSeedbedStudentProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedStudentProfileResponse>> getAllResearchSeedbedStudentProfiles() {
        List<ResearchSeedbedStudentProfileResponse> responses = researchSeedbedStudentProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL )")
    @GetMapping(path = "/research-seedbed-profile/{researchSeedbedProfileId}", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedStudentProfileResponse>> getAllResearchSeedbedStudentProfilesByResearchSeedbedProfileId(@PathVariable Long researchSeedbedProfileId) {
        List<ResearchSeedbedStudentProfileResponse> responses = researchSeedbedStudentProfileHandler.findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).TUTOR_DE_SEMILLERO)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedStudentProfileResponse> createResearchSeedbedStudentProfile
            (@RequestBody @Valid ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        ResearchSeedbedStudentProfileResponse created = researchSeedbedStudentProfileHandler.save(researchSeedbedStudentProfileRequest);
        URI location = URI.create("/api/research-seedbed-student-profile/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).TUTOR_DE_SEMILLERO)")
    @PostMapping(path = "/add-all-by-excel/{researchSeedbedProfileId}", headers = "API-VERSION=1")
    ResponseEntity<List<ResearchSeedbedStudentProfileSummaryResponse>> addAllResearchSeedbedStudentProfileByExcel(
            @PathVariable Long researchSeedbedProfileId, @RequestParam("file") MultipartFile file) {
        List<ResearchSeedbedStudentProfileSummaryResponse> responses = researchSeedbedStudentProfileHandler
                .saveAllByExcel(researchSeedbedProfileId, file);
        URI location = URI.create("/api/research-seedbed-student-profile/");
        return ResponseEntity.created(location).body(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).TUTOR_DE_SEMILLERO)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedStudentProfileResponse> updateResearchSeedbedStudentProfileById
            (@PathVariable Long id, @RequestBody ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        ResearchSeedbedStudentProfileResponse updated = researchSeedbedStudentProfileHandler.updateById(id, researchSeedbedStudentProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).TUTOR_DE_SEMILLERO)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteResearchSeedbedStudentProfileById(@PathVariable Long id) {
        researchSeedbedStudentProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
