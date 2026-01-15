package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.AcademicProgram;

import java.util.List;
import java.util.Set;

/**
 * Service port interface that defines the contract for academic program management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to academic programs,
 * which represent educational programs offered by the institution (undergraduate, graduate programs, etc.).
 * </p>
 *
 * @see AcademicProgram
 */
public interface IAcademicProgramServicePort {
    
    /**
     * Retrieves an academic program by its unique identifier.
     *
     * @param id the unique identifier of the academic program
     * @return the academic program with the specified ID
     */
    AcademicProgram findById(Long id);
    
    /**
     * Persists a new academic program.
     *
     * @param academicProgram the academic program to save
     * @return the saved academic program
     */
    AcademicProgram save(AcademicProgram academicProgram);
    
    /**
     * Updates an existing academic program.
     *
     * @param id the unique identifier of the academic program to update
     * @param academicProgram the academic program data to update
     * @return the updated academic program
     */
    AcademicProgram update(Long id, AcademicProgram academicProgram);
    
    /**
     * Deletes an academic program by its unique identifier.
     *
     * @param id the unique identifier of the academic program to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all academic programs in the system.
     *
     * @return a list of all academic programs
     */
    List<AcademicProgram> findAll();
    
    /**
     * Retrieves multiple academic programs by their unique identifiers.
     *
     * @param ids a set of academic program identifiers
     * @return a set of academic programs matching the provided IDs
     */
    Set<AcademicProgram> findAcademicProgramsByIds(Set<Long> ids);
    
    /**
     * Retrieves multiple academic programs by their program codes.
     *
     * @param academicProgramCodes a set of academic program codes
     * @return a set of academic programs matching the provided codes
     */
    Set<AcademicProgram> findAcademicProgramsByAcademicProgramCodes(Set<String> academicProgramCodes);
    
    /**
     * Checks if an academic program exists with the specified code and name.
     *
     * @param programCode the program code to check
     * @param programName the program name to check
     * @return {@code true} if a program exists with both the code and name, {@code false} otherwise
     */
    boolean existsByProgramCodeAndProgramName(String programCode, String programName);

    /**
     * Retrieves an academic program by its program code.
     *
     * @param academicProgramCode the unique program code
     * @return the academic program with the specified code
     */
    AcademicProgram findByAcademicProgramCode(String academicProgramCode);
}
