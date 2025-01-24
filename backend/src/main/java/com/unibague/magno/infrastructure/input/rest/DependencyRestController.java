package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.application.dto.response.DependencyResponse;
import com.unibague.magno.application.handler.DependencyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dependencies")
public class DependencyRestController {

    private final DependencyHandler dependencyHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public DependencyResponse getDependencyById(@PathVariable Long id) {
        return dependencyHandler.findById(id);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public List<DependencyResponse> getAllDependencies() {
        return dependencyHandler.findAll();
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public DependencyResponse createDependency(@RequestBody DependencyRequest dependencyRequest) {
        return dependencyHandler.save(dependencyRequest);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public DependencyResponse updateDependencyById(@PathVariable Long id, @RequestBody DependencyRequest dependencyRequest) {
        return dependencyHandler.updateById(id, dependencyRequest);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public void deleteDependencyById(@PathVariable Long id) {
        dependencyHandler.deleteById(id);
    }
}
