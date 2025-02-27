package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.InvestigationGroupRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupResponse;
import com.unibague.magno.application.handler.impl.InvestigationGroupHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/investigation-groups")
public class InvestigationGroupRestController {

    private final InvestigationGroupHandler investigationGroupHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupResponse> getInvestigationGroupById(@PathVariable Long id) {
        InvestigationGroupResponse response = investigationGroupHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<InvestigationGroupResponse>> getAllInvestigationGroups() {
        List<InvestigationGroupResponse> responses = investigationGroupHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupResponse> createInvestigationGroup
            (@Valid @RequestBody InvestigationGroupRequest investigationGroupRequest) {
        InvestigationGroupResponse created = investigationGroupHandler.save(investigationGroupRequest);
        URI location = URI.create("/api/investigation-groups/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupResponse> updateInvestigationGroupById
            (@PathVariable Long id, @Valid @RequestBody InvestigationGroupRequest investigationGroupRequest) {
        InvestigationGroupResponse updated = investigationGroupHandler.updateById(id, investigationGroupRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteInvestigationGroupById(@PathVariable Long id) {
        investigationGroupHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
