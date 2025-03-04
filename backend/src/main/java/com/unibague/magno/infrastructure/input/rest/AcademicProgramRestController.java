package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.AcademicProgramRequest;
import com.unibague.magno.application.dto.response.AcademicProgramResponse;
import com.unibague.magno.application.handler.impl.AcademicProgramHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/academic-programs")
public class AcademicProgramRestController {

    private final AcademicProgramHandler academicProgramHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<AcademicProgramResponse> getAcademicProgramById(@PathVariable Long id) {
        AcademicProgramResponse response = academicProgramHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<AcademicProgramResponse>> getAllAcademicPrograms() {
        List<AcademicProgramResponse> responses = academicProgramHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<AcademicProgramResponse> createAcademicProgram
            (@Valid @RequestBody AcademicProgramRequest academicProgramRequest) {
        AcademicProgramResponse created = academicProgramHandler.save(academicProgramRequest);
        URI location = URI.create(String.format("/api/academic-programs/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<AcademicProgramResponse> updateAcademicProgramById
            (@PathVariable Long id, @Valid @RequestBody AcademicProgramRequest academicProgramRequest) {
        AcademicProgramResponse updated = academicProgramHandler.updateById(id, academicProgramRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteAcademicProgramById(@PathVariable Long id) {
        academicProgramHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
