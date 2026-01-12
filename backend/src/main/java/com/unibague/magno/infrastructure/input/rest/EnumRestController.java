package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.domain.api.IEnumServicePort;
import com.unibague.magno.domain.exception.enums.EnumBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enums")
@RequiredArgsConstructor
public class EnumRestController {

    private final IEnumServicePort enumServicePort;
    private static final String ENUM_PACKAGE = "com.unibague.magno.domain.model.enums.";

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/{enumName}/values", headers = "API-VERSION=1")
    public ResponseEntity<List<String>> getEnumValues(@PathVariable String enumName) throws ClassNotFoundException {
        Class<?> enumClass = Class.forName(ENUM_PACKAGE + enumName);
        if (enumClass.isEnum()) {
            return ResponseEntity.ok(enumServicePort.getAllEnumValues((Class<Enum>) enumClass));
        }
        throw new EnumBadRequestException("Enum no encontrado: " + enumName);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/{enumName}/values", headers = "API-VERSION=2")
    public ResponseEntity<Map<String, String>> getEnumValuesAsMap(@PathVariable String enumName) throws ClassNotFoundException {
        Class<?> enumClass = Class.forName(ENUM_PACKAGE + enumName);
        if (enumClass.isEnum()) {
            return ResponseEntity.ok(enumServicePort.getAllEnumValuesAsMap((Class<Enum>) enumClass));
        }
        throw new EnumBadRequestException("Enum no encontrado: " + enumName);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{enumName}/exists", headers = "API-VERSION=1")
    public ResponseEntity<Boolean> existsInEnum(@PathVariable String enumName, @RequestParam String value)
            throws ClassNotFoundException {
        Class<?> enumClass = Class.forName(ENUM_PACKAGE + enumName);
        if (enumClass.isEnum()) {
            return ResponseEntity.ok(enumServicePort.existsInEnum(value, (Class<Enum>) enumClass));
        }
        throw new EnumBadRequestException("Enum no encontrado: " + enumName);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/get-lines-of-research-by-investigation-group-id/{id}", headers = "API-VERSION=1")
    public ResponseEntity<List<String>> getLinesOfResearchByInvestigationGroupId(@PathVariable Long id) {
        return ResponseEntity.ok(enumServicePort.getLinesOfResearchByInvestigationGroupId(id));
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/get-lines-of-research-by-research-seedbed-id/{id}", headers = "API-VERSION=1")
    public ResponseEntity<String> getLinesOfResearchByResearchSeedbedId(@PathVariable Long id) {
        return ResponseEntity.ok(enumServicePort.getLineOfResearchByResearchSeedbedId(id));
    }
}
