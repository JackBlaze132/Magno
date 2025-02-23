package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.handler.ResearchSeedbedStudentProfileHandler;
import com.unibague.magno.infrastructure.util.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping(path = "/add-by-excel/", headers = "API-VERSION=1")
    public String addResearchSeedbedStudentProfileByExcel(
            @RequestParam("file") MultipartFile file) {
        try{
            List<Map<String, String>> data = uploadService.uploadExcel(file);
            List<Map<String, String>> newData = new ArrayList<>(data);
            newData.removeIf(map -> map.entrySet()
                    .stream()
                    .anyMatch(entry -> entry.getValue().isEmpty()));
            return newData.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error xd";
        }
    }

    @PostMapping(path = "/add-all-by-excel/{researchSeedbedProfileId}", headers = "API-VERSION=1")
    ResponseEntity<List<ResearchSeedbedStudentProfileResponse>> addAllResearchSeedbedStudentProfileByExcel(
            @PathVariable Long researchSeedbedProfileId, @RequestParam("file") MultipartFile file) {
        List<ResearchSeedbedStudentProfileResponse> responses = researchSeedbedStudentProfileHandler
                .saveAllByExcel(researchSeedbedProfileId, file);
        return ResponseEntity.ok(responses);
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
