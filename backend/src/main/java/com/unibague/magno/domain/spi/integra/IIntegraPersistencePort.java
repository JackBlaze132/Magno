package com.unibague.magno.domain.spi.integra;

import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Persistence port for integrating with the university's Integra system.
 * <p>
 * This interface defines the contract for retrieving data from Integra, the university's
 * central information system. It provides access to official records of functionaries,
 * students, academic programs, and dependencies that are used to synchronize and validate
 * data in Magno.
 * </p>
 * <p>
 * <strong>Note:</strong> This port is read-only as Integra is an external system of record.
 * All data modifications should be made in Integra, and Magno synchronizes from it.
 * </p>
 */
public interface IIntegraPersistencePort {

    /**
     * Retrieves all functionaries from the Integra system.
     *
     * @return a list of all functionaries registered in Integra
     */
    List<IntegraFunctionary> getAllFunctionaries();

    /**
     * Retrieves student records from Integra by identification number.
     * <p>
     * A student may have multiple records if they are enrolled in more than one
     * academic program simultaneously (e.g., double degree).
     * </p>
     *
     * @param identification the student's identification document number
     * @return a list of student records associated with the identification
     */
    List<IntegraStudent> getIntegraStudentRecordsByIdentification(String identification);

    /**
     * Retrieves academic programs from Integra by their program codes.
     *
     * @param programCodes the set of program codes to search for
     * @return a list of academic programs matching the provided codes
     */
    List<IntegraAcademicProgram> getIntegraAcademicProgramsByProgramCodes(Set<String> programCodes);

    /**
     * Retrieves a dependency from Integra by its name.
     *
     * @param dependencyName the name of the dependency to search for
     * @return the dependency with the specified name
     */
    IntegraDependency getIntegraDependencyByDependencyName(String dependencyName);

    /**
     * Finds a functionary in Integra by their email address.
     *
     * @param email the functionary's email address
     * @return an {@link Optional} containing the functionary if found, or empty otherwise
     */
    Optional<IntegraFunctionary> getIntegraFunctionaryByEmail(String email);

    /**
     * Finds a student in Integra by their email address.
     *
     * @param email the student's email address
     * @return an {@link Optional} containing the student if found, or empty otherwise
     */
    Optional<IntegraStudent> getIntegraStudentByEmail(String email);

    /**
     * Retrieves all students from the Integra system.
     *
     * @return a list of all students registered in Integra
     */
    List<IntegraStudent> getAllStudents();

    /**
     * Retrieves all academic programs from Integra, organized by program type.
     * <p>
     * This method groups programs by their type (undergraduate, graduate, etc.)
     * for easier filtering and display.
     * </p>
     *
     * @return a map where keys are program types and values are lists of programs of that type
     */
    Map<AcademicProgramType, List<IntegraAcademicProgram>> getAllAcademicProgramsMappedByType();
}
