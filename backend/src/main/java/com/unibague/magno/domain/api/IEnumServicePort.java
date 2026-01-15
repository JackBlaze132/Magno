package com.unibague.magno.domain.api;

import java.util.List;
import java.util.Map;

/**
 * Service port interface that defines the contract for enum-related operations.
 * <p>
 * This interface provides utility methods for working with enum values, including
 * validation, retrieval, and mapping operations for various enum types used throughout
 * the application.
 * </p>
 */
public interface IEnumServicePort {
    
    /**
     * Retrieves all values of a specific enum type as strings.
     *
     * @param <E> the enum type
     * @param enumClass the class of the enum
     * @return a list of all enum value names
     */
    <E extends Enum<E>> List<String> getAllEnumValues(Class<E> enumClass);
    
    /**
     * Checks if a string value exists in a specific enum type.
     *
     * @param <E> the enum type
     * @param value the string value to check
     * @param enumClass the class of the enum
     * @return {@code true} if the value exists in the enum, {@code false} otherwise
     */
    <E extends Enum<E>> boolean existsInEnum(String value, Class<E> enumClass);
    
    /**
     * Retrieves all lines of research associated with a specific investigation group.
     *
     * @param investigationGroupId the unique identifier of the investigation group
     * @return a list of lines of research for the specified group
     */
    List<String> getLinesOfResearchByInvestigationGroupId(Long investigationGroupId);
    
    /**
     * Retrieves the line of research associated with a specific research seedbed.
     *
     * @param researchSeedbedId the unique identifier of the research seedbed
     * @return the line of research for the specified seedbed
     */
    String getLineOfResearchByResearchSeedbedId(Long researchSeedbedId);
    
    /**
     * Retrieves all values of a specific enum type as a map.
     * <p>
     * The map typically contains enum names as keys and their formatted/display names as values.
     * </p>
     *
     * @param <E> the enum type
     * @param enumValue the class of the enum
     * @return a map of enum values with their names and formatted values
     */
    <E extends Enum<E>> Map<String, String> getAllEnumValuesAsMap(Class<E> enumValue);
}
