package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IEnumServicePort;
import com.unibague.magno.domain.api.IInvestigationGroupServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumUseCase implements IEnumServicePort {

    private static final String ERROR_MESSAGE = "El enum no tiene un metodo getFormattedName.";
    private static final String GET_FORMATTED_NAME = "getFormattedName";

    private final IInvestigationGroupServicePort investigationGroupServicePort;
    private final IResearchSeedbedServicePort researchSeedbedServicePort;

    public EnumUseCase(IInvestigationGroupServicePort investigationGroupServicePort,
                       IResearchSeedbedServicePort researchSeedbedServicePort) {
        this.investigationGroupServicePort = investigationGroupServicePort;
        this.researchSeedbedServicePort = researchSeedbedServicePort;
    }

    @Override
    public <E extends Enum<E>> List<String> getAllEnumValues(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(e -> {
                    try {
                        return (String) enumClass.getMethod(GET_FORMATTED_NAME).invoke(e);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException(ERROR_MESSAGE);
                    }
                }).toList();
    }

    @Override
    public <E extends Enum<E>> boolean existsInEnum(String value, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> {
                    try {
                        // The method .replace(" ", "_") is used to have a better syntax in postman
                        String formattedName = ((String) enumClass.getMethod(GET_FORMATTED_NAME).invoke(e))
                                .replace(" ", "_");
                        return formattedName.equalsIgnoreCase(value);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException(ERROR_MESSAGE);
                    }
                });
    }

    private <E extends Enum<E>> String formatEnumSet(Set<E> enumSet) {
        return enumSet.stream()
                .map(e -> {
                    try {
                        return (String) e.getClass().getMethod(GET_FORMATTED_NAME).invoke(e);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException(ERROR_MESSAGE);
                    }
                })
                .collect(Collectors.joining(" - "));
    }

    private <E extends Enum<E>> Map<String, String> enumValuesAsMap(Class<E> enumClass) {
        return Stream.of(enumClass.getEnumConstants())
                .collect(Collectors.toMap(
                        Enum::name,
                        v -> {
                            try {
                                return (String) v.getClass().getMethod(GET_FORMATTED_NAME).invoke(v);
                            } catch (Exception ex) {
                                throw new IllegalArgumentException(ERROR_MESSAGE, ex);
                            }
                        }
                ));
    }


    @Override
    public String getFormattedEnumSetByInvestigationGroupId(Long investigationGroupId){
        return formatEnumSet(investigationGroupServicePort.findById(investigationGroupId).getLinesOfResearch());
    }

    @Override
    public String getFormattedEnumSetByResearchSeedbedId(Long researchSeedbedId){
        return formatEnumSet(Set.of(researchSeedbedServicePort.findById(researchSeedbedId).getLineOfResearch()));
    }

    @Override
    public <E extends Enum<E>> Map<String, String> getAllEnumValuesAMap(Class<E> enumValue){
        return enumValuesAsMap(enumValue);
    }
}
