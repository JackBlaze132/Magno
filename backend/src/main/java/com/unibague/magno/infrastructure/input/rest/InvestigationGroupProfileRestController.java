package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.application.dto.util.CurrentUserInfo;
import com.unibague.magno.application.handler.impl.InvestigationGroupProfileHandler;
import com.unibague.magno.domain.exception.security.NotAllowedToDoThisActionException;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;
import com.unibague.magno.infrastructure.configuration.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * REST controller for managing investigation group profiles in Magno.
 * Provides endpoints for CRUD operations on investigation group profiles,
 * which represent the state and configuration of an investigation group
 * for a specific academic period. Also includes endpoints for generating
 * Excel reports on investigation groups and active seedbeds.
 *
 * @see InvestigationGroupProfileHandler
 */
@RestController
@RequestMapping("/investigation-group-profiles")
@RequiredArgsConstructor
public class InvestigationGroupProfileRestController {

    private final InvestigationGroupProfileHandler investigationGroupProfileHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupProfileResponse> getInvestigationGroupProfileById(@PathVariable Long id) {
        InvestigationGroupProfileResponse response = investigationGroupProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<InvestigationGroupProfileResponse>> getAllInvestigationGroupProfiles() {
        List<InvestigationGroupProfileResponse> responses = investigationGroupProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/get-all-by-academic-period-id/{id}", headers = "API-VERSION=1")
    public ResponseEntity<List<InvestigationGroupProfileResponse>> getAllInvestigationGroupProfilesByAcademicPeriodId(
            @PathVariable Long id) {
        List<InvestigationGroupProfileResponse> responses = investigationGroupProfileHandler.findAllByAcademicPeriodId(id);
        return ResponseEntity.ok(responses);
    }

    /**
     * Generates an Excel report for investigation groups for a single academic period (half-year).
     *
     * @param academicPeriodId The ID of the academic period to generate the report for.
     * @return Excel file as byte array with investigation group data.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/generate-investigation-group-half-year-report", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getHalfYearInvestigationGroupReport(@RequestParam("apId") Long academicPeriodId) {
        ExcelReport<InvestigationGroupHYRMetadata> report = investigationGroupProfileHandler
                .getExcelBytesForHalfYearInvestigationGroupReport(academicPeriodId);
        InvestigationGroupHYRMetadata metadata = report.getMetadata();

        String filename = String.format("Reporte_de_Grupos_de_Investigacion_%s.xlsx",
                metadata.getAcademicPeriodName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(report.getContent());
    }

    /**
     * Generates an annual Excel report for investigation groups comparing two academic periods.
     *
     * @param academicPeriodId1 The ID of the first academic period.
     * @param academicPeriodId2 The ID of the second academic period.
     * @return Excel file as byte array with comparative investigation group data.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/generate-investigation-group-annual-year-report", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getAnnualInvestigationGroupReport(@RequestParam("apId1") Long academicPeriodId1,
                                                                    @RequestParam("apId2") Long academicPeriodId2) {
        ExcelReport<InvestigationGroupYRMetadata> report = investigationGroupProfileHandler
                .getExcelBytesForAnnualInvestigationGroupReport(academicPeriodId1, academicPeriodId2);
        InvestigationGroupYRMetadata metadata = report.getMetadata();

        String filename = String.format("Reporte_de_Grupos_de_Investigacion_%s__%s.xlsx",
                metadata.getAcademicPeriodName1(), metadata.getAcademicPeriodName2());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(report.getContent());
    }

    /**
     * Generates an Excel report of active seedbeds for a single academic period (half-year).
     *
     * @param academicPeriodId The ID of the academic period to generate the report for.
     * @return Excel file as byte array with active seedbeds data.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/generate-active-seedbeds-half-year-report", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getHalfYearActiveSeedbedsReport(@RequestParam("apId") Long academicPeriodId) {
        ExcelReport<ActiveSeedbedsMetadata> report = investigationGroupProfileHandler
                .getExcelBytesForHalfYearActiveSeedbedsReport(academicPeriodId);
        ActiveSeedbedsMetadata metadata = report.getMetadata();

        String filename = String.format("Semilleros_activos_%s.xlsx",
                metadata.getAcademicPeriodName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(report.getContent());
    }

    /**
     * Generates an annual Excel report of active seedbeds comparing two academic periods.
     *
     * @param academicPeriodId1 The ID of the first academic period.
     * @param academicPeriodId2 The ID of the second academic period.
     * @return Excel file as byte array with comparative active seedbeds data.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/generate-active-seedbeds-annual-year-report", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getAnnualActiveSeedbedsReport(@RequestParam("apId1") Long academicPeriodId1,
                                                                @RequestParam("apId2") Long academicPeriodId2) {
        ExcelReport<ActiveSeedbedsMetadata> report = investigationGroupProfileHandler
                .getExcelBytesForAnnualActiveSeedbedsReport(academicPeriodId1, academicPeriodId2);
        ActiveSeedbedsMetadata metadata = report.getMetadata();

        String filename = String.format("Semilleros_activos_%s.xlsx",
                metadata.getAcademicPeriodName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(report.getContent());
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupProfileResponse> createInvestigationGroupProfile
            (@Valid @RequestBody InvestigationGroupProfileRequest investigationGroupProfileRequest,
             @CurrentUser CurrentUserInfo currentUserInfo) {
        /**
        if (!currentUserInfo.getEmail().endsWith("@unibague.edu.co")) {
            throw new NotAllowedToDoThisActionException("Solo los funcionarios pueden crear perfiles de grupo de investigación.");
        }*/
        InvestigationGroupProfileResponse created = investigationGroupProfileHandler
                .save(investigationGroupProfileRequest);
        URI location = URI.create(String.format("/api/investigation-group-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupProfileResponse> updateInvestigationGroupProfileById
            (@PathVariable Long id, @Valid @RequestBody InvestigationGroupProfileRequest investigationGroupProfileRequest) {
        InvestigationGroupProfileResponse updated = investigationGroupProfileHandler
                .updateById(id, investigationGroupProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteInvestigationGroupProfileById(@PathVariable Long id) {
        investigationGroupProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
