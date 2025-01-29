package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.domain.api.IEnumServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enums")
@RequiredArgsConstructor
public class EnumRestController {

    private final IEnumServicePort enumServicePort;

    @GetMapping("/{enumName}/values")
    public List<String> getEnumValues(@PathVariable String enumName) {
        try {
            Class<?> enumClass = Class.forName("com.unibague.magno.domain.model.enums." + enumName);
            if (enumClass.isEnum()) {
                return enumServicePort.getAllEnumValues((Class<Enum>) enumClass);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Enum no encontrado: " + enumName);
        }
        throw new IllegalArgumentException("La clase proporcionada no es un enum.");
    }

    @GetMapping("/{enumName}/exists")
    public boolean existsInEnum(@PathVariable String enumName, @RequestParam String value) {
        try {
            Class<?> enumClass = Class.forName("com.unibague.magno.domain.model.enums." + enumName);
            if (enumClass.isEnum()) {
                return enumServicePort.existsInEnum(value, (Class<Enum>) enumClass);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Enum no encontrado: " + enumName);
        }
        throw new IllegalArgumentException("La clase proporcionada no es un enum.");
    }
}
