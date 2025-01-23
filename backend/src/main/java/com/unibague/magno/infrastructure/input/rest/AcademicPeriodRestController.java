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

    @GetMapping(path = "/academic-periods/{id}")
    public AcademicPeriodResponse getAcademicPeriodById(@PathVariable Long id) {
        return academicPeriodHandler.findById(id);
    }

    @GetMapping(path = "/academic-periods")
    public List<AcademicPeriodResponse> getAllAcademicPeriods() {
        return academicPeriodHandler.findAll();
    }

    @PostMapping(path = "/academic-periods")
    public AcademicPeriodResponse createAcademicPeriod(@RequestBody AcademicPeriodRequest academicPeriodRequest) {
        return academicPeriodHandler.save(academicPeriodRequest);
    }

    @DeleteMapping(path = "/academic-periods/{id}")
    public void deleteAcademicPeriodById(@PathVariable Long id) {
        academicPeriodHandler.deleteById(id);
    }
}
