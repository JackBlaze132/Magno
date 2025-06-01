package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.handler.impl.ResearchSeedbedProfileHandler;
import com.unibague.magno.domain.model.projections.SeedbedReportProjection;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.io.ByteArrayOutputStream;
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

    @GetMapping(path = "/get-all-by-investigation-group-profile-id/{id}", headers = "API-VERSION=1")
    public ResponseEntity<List<ResearchSeedbedProfileResponse>> getAllByInvestigationGroupProfileId(@PathVariable Long id) {
        List<ResearchSeedbedProfileResponse> responses = researchSeedbedProfileHandler.findAllByInvestigationGroupProfileId(id);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/excel", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> generateExcel(
            @RequestParam("rspId") Long researchSeedbedProfileId,
            @RequestParam("apId") Long academicPeriodId) throws Exception{

        // Create a new workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"Periodo académico", "Grupo de investigación", "Semillero", "Coordinador", "Estudiante",
            "Código", "Programa académico", "Semestre", "Sexo"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        // Data to be filled in
        List<SeedbedReportProjection> records = this.getSeedbedReport(researchSeedbedProfileId, academicPeriodId);

        String researchSeedbedName = records.isEmpty() ? "" : records.getFirst().getResearchSeedbedName().replaceAll("[\\\\/:*?\"<>|]", "");
        String academicPeriodName = records.isEmpty() ? "" : records.getFirst().getAcademicPeriodName().replaceAll("[\\\\/:*?\"<>|]", "");
        final String docTitle = String.format("Reporte_de_Semillero_%s_Periodo_Academico_%s.xlsx",
                researchSeedbedName, academicPeriodName);
        final String encodedFilename = URLEncoder.encode(docTitle, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
        System.out.println(encodedFilename);


        // Fill the sheet with data
        int rowNum = 1;
        for (SeedbedReportProjection record : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(record.getAcademicPeriodName());
            row.createCell(1).setCellValue(record.getInvestigationGroupName());
            row.createCell(2).setCellValue(record.getResearchSeedbedName());
            row.createCell(3).setCellValue(record.getCoordinatorName());
            row.createCell(4).setCellValue(record.getStudentName());
            row.createCell(5).setCellValue(record.getCode());
            row.createCell(6).setCellValue(record.getAcademicProgramName());
            row.createCell(7).setCellValue(record.getSemester() != null ? record.getSemester() : 0);
            row.createCell(8).setCellValue(record.getSex());
        }

        // Auto-size columns for better readability
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to a byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        // Configure the response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + docTitle + "\"; filename*=UTF-8''" + encodedFilename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bos.toByteArray());
    }

    @GetMapping(path = "/report", headers = "API-VERSION=1")
    public List<SeedbedReportProjection> getSeedbedReport(
            @RequestParam("rspId") Long researchSeedbedProfileId,
            @RequestParam("apId") Long academicPeriodId) {
        return researchSeedbedProfileHandler.getSeedbedReport(researchSeedbedProfileId, academicPeriodId);
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
