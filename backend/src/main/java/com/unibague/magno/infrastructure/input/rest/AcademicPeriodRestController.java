package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.handler.AcademicPeriodHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/academic-periods")
public class AcademicPeriodRestController {

    private final AcademicPeriodHandler academicPeriodHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<AcademicPeriodResponse> getAcademicPeriodById(@PathVariable Long id) {
        AcademicPeriodResponse response = academicPeriodHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<AcademicPeriodResponse>> getAllAcademicPeriods() {
        List<AcademicPeriodResponse> responses = academicPeriodHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<AcademicPeriodResponse> createAcademicPeriod
            (@RequestBody AcademicPeriodRequest academicPeriodRequest) {
        AcademicPeriodResponse created = academicPeriodHandler.save(academicPeriodRequest);
        URI location = URI.create(String.format("/api/academic-periods/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<AcademicPeriodResponse> updateAcademicPeriodById
            (@PathVariable Long id, @RequestBody AcademicPeriodRequest academicPeriodRequest) {
        AcademicPeriodResponse updated = academicPeriodHandler.updateById(id, academicPeriodRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteAcademicPeriodById(@PathVariable Long id) {
        academicPeriodHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
