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
    private final String enumPackage = "com.unibague.magno.domain.model.enums.";

    @GetMapping("/{enumName}/values")
    public ResponseEntity<List<String>> getEnumValues(@PathVariable String enumName) {
        try {
            Class<?> enumClass = Class.forName(enumPackage + enumName);
            if (enumClass.isEnum()) {
                return ResponseEntity.ok(enumServicePort.getAllEnumValues((Class<Enum>) enumClass));
            }
        } catch (ClassNotFoundException e) {
            throw new EnumBadRequestException("Enum no encontrado: " + enumName);
        }
        throw new IllegalArgumentException("La clase proporcionada no es un enum.");
    }

    @GetMapping("/{enumName}/exists")
    public ResponseEntity<Boolean> existsInEnum(@PathVariable String enumName, @RequestParam String value) {
        try {
            Class<?> enumClass = Class.forName(enumPackage + enumName);
            if (enumClass.isEnum()) {
                return ResponseEntity.ok(enumServicePort.existsInEnum(value, (Class<Enum>) enumClass));
            }
        } catch (ClassNotFoundException e) {
            throw new EnumBadRequestException("Enum no encontrado: " + enumName);
        }
        throw new IllegalArgumentException("La clase proporcionada no es un enum.");
    }
}
