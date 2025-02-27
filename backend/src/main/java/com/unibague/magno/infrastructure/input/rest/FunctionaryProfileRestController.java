package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.application.handler.impl.FunctionaryProfileHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/functionary-profiles")
public class FunctionaryProfileRestController {

    private final FunctionaryProfileHandler functionaryProfileHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<FunctionaryProfileResponse> getFunctionaryProfileById(@PathVariable Long id) {
        FunctionaryProfileResponse response = functionaryProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<FunctionaryProfileResponse>> getAllFunctionaryProfiles() {
        List<FunctionaryProfileResponse> responses = functionaryProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<FunctionaryProfileResponse> createFunctionaryProfile
            (@Valid @RequestBody FunctionaryProfileRequest functionaryProfileRequest) {
        FunctionaryProfileResponse created = functionaryProfileHandler.save(functionaryProfileRequest);
        URI location = URI.create(String.format("/api/functionary-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<FunctionaryProfileResponse> updateFunctionaryProfileById
            (@PathVariable Long id, @Valid @RequestBody FunctionaryProfileRequest functionaryProfileRequest) {
        FunctionaryProfileResponse updated = functionaryProfileHandler.updateById(id, functionaryProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteFunctionaryProfileById(@PathVariable Long id) {
        functionaryProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
