package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IEnumServicePort;

import java.util.Arrays;
import java.util.List;

public class EnumUseCase implements IEnumServicePort {
    @Override
    public <E extends Enum<E>> List<String> getAllEnumValues(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(e -> {
                    try {
                        return (String) enumClass.getMethod("getFormattedName").invoke(e);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("El enum no tiene un método getFormattedName.");
                    }
                }).toList();
    }

    @Override
    public <E extends Enum<E>> boolean existsInEnum(String value, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> {
                    try {
                        // The method .replace(" ", "_") is used to have a better syntax in postman
                        String formattedName = ((String) enumClass.getMethod("getFormattedName").invoke(e))
                                .replace(" ", "_");
                        return formattedName.equalsIgnoreCase(value);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("El enum no tiene un método getFormattedName.");
                    }
                });
    }
}
