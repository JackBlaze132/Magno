package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.domain.api.IEnumServicePort;
import com.unibague.magno.domain.exception.EnumBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enums")
@RequiredArgsConstructor
public class EnumRestController {

    private final IEnumServicePort enumServicePort;
    private static final String ENUM_PACKAGE = "com.unibague.magno.domain.model.enums.";

    @GetMapping(path = "/{enumName}/values", headers = "API-VERSION=1")
    public ResponseEntity<List<String>> getEnumValues(@PathVariable String enumName) throws ClassNotFoundException {
        Class<?> enumClass = Class.forName(ENUM_PACKAGE + enumName);
        if (enumClass.isEnum()) {
            return ResponseEntity.ok(enumServicePort.getAllEnumValues((Class<Enum>) enumClass));
        }
        throw new EnumBadRequestException("Enum no encontrado: " + enumName);
    }

    @GetMapping(path = "/{enumName}/exists", headers = "API-VERSION=1")
    public ResponseEntity<Boolean> existsInEnum(@PathVariable String enumName, @RequestParam String value)
            throws ClassNotFoundException {
        Class<?> enumClass = Class.forName(ENUM_PACKAGE + enumName);
        if (enumClass.isEnum()) {
            return ResponseEntity.ok(enumServicePort.existsInEnum(value, (Class<Enum>) enumClass));
        }
        throw new EnumBadRequestException("Enum no encontrado: " + enumName);
    }
}
