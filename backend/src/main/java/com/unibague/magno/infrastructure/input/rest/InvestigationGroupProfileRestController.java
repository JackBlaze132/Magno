package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.application.handler.impl.InvestigationGroupProfileHandler;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/investigation-group-profiles")
@RequiredArgsConstructor
public class InvestigationGroupProfileRestController {

    private final InvestigationGroupProfileHandler investigationGroupProfileHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupProfileResponse> getInvestigationGroupProfileById(@PathVariable Long id) {
        InvestigationGroupProfileResponse response = investigationGroupProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<InvestigationGroupProfileResponse>> getAllInvestigationGroupProfiles() {
        List<InvestigationGroupProfileResponse> responses = investigationGroupProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/get-all-by-academic-period-id/{id}", headers = "API-VERSION=1")
    public ResponseEntity<List<InvestigationGroupProfileResponse>> getAllInvestigationGroupProfilesByAcademicPeriodId(
            @PathVariable Long id) {
        List<InvestigationGroupProfileResponse> responses = investigationGroupProfileHandler.findAllByAcademicPeriodId(id);
        return ResponseEntity.ok(responses);
    }
    @GetMapping(path = "generate-half-year-investigation-report", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getHalfYearInvestigationGroupReport(@RequestParam("apId") Long academicPeriodId) {
        ExcelReport<InvestigationGroupHYRMetadata> report = investigationGroupProfileHandler
                .getExcelBytesForHalfYearInvestigationGroupReport(academicPeriodId);
        InvestigationGroupHYRMetadata metadata = report.getMetadata();

        String filename = String.format("Reporte_de_Grupos_de_Investigacion_%s.xlsx",
                metadata.getAcademicPeriodName(), metadata.getAcademicPeriodName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(report.getContent());
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupProfileResponse> createInvestigationGroupProfile
            (@Valid @RequestBody InvestigationGroupProfileRequest investigationGroupProfileRequest) {
        InvestigationGroupProfileResponse created = investigationGroupProfileHandler
                .save(investigationGroupProfileRequest);
        URI location = URI.create(String.format("/api/investigation-group-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupProfileResponse> updateInvestigationGroupProfileById
            (@PathVariable Long id, @Valid @RequestBody InvestigationGroupProfileRequest investigationGroupProfileRequest) {
        InvestigationGroupProfileResponse updated = investigationGroupProfileHandler
                .updateById(id, investigationGroupProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteInvestigationGroupProfileById(@PathVariable Long id) {
        investigationGroupProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
