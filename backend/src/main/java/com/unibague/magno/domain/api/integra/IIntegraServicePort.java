package com.unibague.magno.domain.api.integra;

import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service port interface that defines the contract for Integra external system integration operations.
 * <p>
 * This interface provides methods for retrieving and processing data from the institutional
 * Integra system, including student information, functionary data, academic programs, and
 * dependencies. It serves as the main integration point with the external academic management system.
 * </p>
 *
 * @see IntegraStudent
 * @see IntegraFunctionary
 * @see IntegraAcademicProgram
 * @see IntegraDependency
 */
public interface IIntegraServicePort {
    
    /**
     * Retrieves all functionaries from the Integra system.
     *
     * @return a list of all functionaries registered in Integra
     */
    List<IntegraFunctionary> getAllFunctionaries();
    
    /**
     * Retrieves a functionary from Integra by identification number.
     *
     * @param identification the identification number of the functionary
     * @return the functionary with the specified identification
     */
    IntegraFunctionary getIntegraFunctionaryByIdentification(String identification);
    
    /**
     * Retrieves student records from Integra by identification number.
     * <p>
     * This method returns a list because a student can be enrolled in multiple programs,
     * resulting in multiple records in the JSON response from Integra.
     * </p>
     *
     * @param identification the identification number of the student
     * @return a list of student records for the specified identification
     */
    List<IntegraStudent> getIntegraStudentRecordsByIdentification(String identification);
    
    /**
     * Identifies which student identification numbers are not found in Integra.
     *
     * @param identifications a list of identification numbers to check
     * @return a list of identification numbers that were not found in Integra
     */
    List<String> findMissingStudentIdentificationsInIntegra(List<String> identifications);
    
    /**
     * Retrieves the first student record found in Integra for a given identification.
     * <p>
     * This method should only be used by the User class when a single record is needed
     * and the student's specific program doesn't matter.
     * </p>
     *
     * @param identification the identification number of the student
     * @return the first student record found
     */
    IntegraStudent getFirstIntegraStudentFound(String identification);
    
    /**
     * Cleans and validates a list of student data maps against Integra records.
     * <p>
     * This method processes raw student data, validates it against Integra, and returns
     * only the records that correspond to valid students in the system.
     * </p>
     *
     * @param researchSeedbedStudentProfiles a list of maps containing student profile data
     * @return a cleaned list of valid student data maps
     */
    List<Map<String, String>> getCleanedStudentListOfMaps(List<Map<String, String>> researchSeedbedStudentProfiles);
    
    /**
     * Calculates the maximum semester among a list of student records.
     *
     * @param studentRecords a list of student records to analyze
     * @return the highest semester number found among the records
     */
    byte getMaxSemester(List<IntegraStudent> studentRecords);
    
    /**
     * Retrieves academic programs from Integra by their program codes.
     *
     * @param programCodes a set of program codes to retrieve
     * @return a list of academic programs matching the specified codes
     */
    List<IntegraAcademicProgram> getIntegraAcademicProgramsByProgramCodes(Set<String> programCodes);
    
    /**
     * Retrieves all academic programs from Integra grouped by program type.
     *
     * @return a map with program types as keys and lists of academic programs as values
     */
    Map<AcademicProgramType, List<IntegraAcademicProgram>> getAllAcademicProgramsMappedByType();
    
    /**
     * Retrieves a dependency from Integra by its name.
     *
     * @param dependencyName the name of the dependency
     * @return the dependency with the specified name
     */
    IntegraDependency getIntegraDependencyByDependencyName(String dependencyName);

    /**
     * Retrieves a functionary from Integra by email address.
     *
     * @param email the email address of the functionary
     * @return the functionary with the specified email
     */
    IntegraFunctionary getIntegraFunctionaryByEmail(String email);

    /**
     * Retrieves a student from Integra by email address.
     *
     * @param email the email address of the student
     * @return the student with the specified email
     */
    IntegraStudent getIntegraStudentByEmail(String email);

    /**
     * Retrieves all students from the Integra system.
     *
     * @return a list of all students registered in Integra
     */
    List<IntegraStudent> getAllStudents();
}
