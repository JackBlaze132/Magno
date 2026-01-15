package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.AcademicProgram;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Persistence port for managing academic program data.
 * <p>
 * This interface defines the contract for persisting and retrieving academic programs.
 * Academic programs represent the university's degree programs (undergraduate, graduate, etc.)
 * that students are enrolled in and that may be associated with research seedbeds.
 * </p>
 */
public interface IAcademicProgramPersistencePort {
    Optional<AcademicProgram> findById(Long id);
    AcademicProgram save(AcademicProgram academicProgram);
    AcademicProgram update(Long id, AcademicProgram academicProgram);
    void deleteById(Long id);
    List<AcademicProgram> findAll();

    /**
     * Retrieves multiple academic programs by their IDs.
     *
     * @param ids the set of academic program IDs to retrieve
     * @return a set of academic programs matching the provided IDs
     */
    Set<AcademicProgram> findAcademicProgramsByIds(Set<Long> ids);

    /**
     * Retrieves multiple academic programs by their program codes.
     *
     * @param academicProgramCodes the set of program codes to search for
     * @return a set of academic programs matching the provided codes
     */
    Set<AcademicProgram> findAcademicProgramsByAcademicProgramCodes(Set<String> academicProgramCodes);

    /**
     * Checks if an academic program exists with the given code and name combination.
     *
     * @param programCode the program code to check
     * @param programName the program name to check
     * @return {@code true} if a program with both the code and name exists, {@code false} otherwise
     */
    boolean existsByProgramCodeAndProgramName(String programCode, String programName);

    /**
     * Finds an academic program by its unique program code.
     *
     * @param academicProgramCode the program code to search for
     * @return the academic program with the specified code
     */
    AcademicProgram findByAcademicProgramCode(String academicProgramCode);
}
