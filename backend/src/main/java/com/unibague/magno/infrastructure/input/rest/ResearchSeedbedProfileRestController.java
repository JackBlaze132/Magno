package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.handler.impl.ResearchSeedbedProfileHandler;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/research-seedbed-profiles")
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

    @GetMapping(path = "/get-all-by-investigation-group-profile-id/{id}", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedProfileResponse>> getAllByInvestigationGroupProfileId(@PathVariable Long id) {
        List<ResearchSeedbedProfileResponse> responses = researchSeedbedProfileHandler.findAllByInvestigationGroupProfileId(id);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/generate-seedbed-report", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getSeedbedReport(
            @RequestParam("rspId") Long researchSeedbedProfileId,
            @RequestParam("apId") Long academicPeriodId) {

        ExcelReport<SeedbedReportMetadata> report = researchSeedbedProfileHandler
                .getExcelBytesReport(researchSeedbedProfileId, academicPeriodId);
        SeedbedReportMetadata metadata = report.getMetadata();

        String filename = String.format("Reporte_de_Semillero_%s_Periodo_Academico_%s.xlsx",
                metadata.getResearchSeedbedName(), metadata.getAcademicPeriodName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(report.getContent());
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ResearchSeedbedProfileResponse> createResearchSeedbedProfile
            (@RequestBody @Valid ResearchSeedbedProfileRequest researchSeedbedProfileRequest) {
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
