package com.unibague.magno.domain.api;

import java.util.List;

public interface IEnumServicePort {
    <E extends Enum<E>> List<String> getAllEnumValues(Class<E> enumClass);
    <E extends Enum<E>> boolean existsInEnum(String value, Class<E> enumClass);
    String getFormattedEnumSetByInvestigationGroupId(Long investigationGroupId);
    String getFormattedEnumSetByResearchSeedbedId(Long researchSeedbedId);
}
