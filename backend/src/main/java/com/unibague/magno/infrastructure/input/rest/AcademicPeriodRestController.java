package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.handler.AcademicPeriodHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/academic-periods")
public class AcademicPeriodRestController {

    private final AcademicPeriodHandler academicPeriodHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public AcademicPeriodResponse getAcademicPeriodById(@PathVariable Long id) {
        return academicPeriodHandler.findById(id);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public List<AcademicPeriodResponse> getAllAcademicPeriods() {
        return academicPeriodHandler.findAll();
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public AcademicPeriodResponse createAcademicPeriod(@RequestBody AcademicPeriodRequest academicPeriodRequest) {
        return academicPeriodHandler.save(academicPeriodRequest);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public AcademicPeriodResponse updateAcademicPeriod(@PathVariable Long id, @RequestBody AcademicPeriodRequest academicPeriodRequest) {
        return academicPeriodHandler.updateById(id, academicPeriodRequest);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public void deleteAcademicPeriodById(@PathVariable Long id) {
        academicPeriodHandler.deleteById(id);
    }
}
