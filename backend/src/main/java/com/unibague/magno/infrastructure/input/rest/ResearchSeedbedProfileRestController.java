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
    public ResponseEntity<byte[]> generateExcel() throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Productos");

        // Crea la fila de encabezados
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Precio"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        // Datos de ejemplo
        List<Object[]> datos = List.of(
                new Object[]{1, "Producto A", 5000},
                new Object[]{2, "Producto B", 8000},
                new Object[]{3, "Producto C", 10000}
        );

        // Agrega los datos
        int rowNum = 1;
        for (Object[] fila : datos) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fila.length; i++) {
                row.createCell(i).setCellValue(fila[i].toString());
            }
        }

        // Ajustar tamaño de columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribe el archivo a memoria
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        // Configura la respuesta HTTP
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=productos.xlsx")
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
