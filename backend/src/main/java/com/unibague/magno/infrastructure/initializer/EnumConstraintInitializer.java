package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.model.enums.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Automatically updates CHECK constraints for all enum columns at application startup.
 * <p>
 * This component ensures that all database constraints stay synchronized with their
 * corresponding enum values. It runs before data initializers to guarantee that all
 * enum values can be inserted without constraint violations.
 * </p>
 * <p>
 * <strong>Why this is needed:</strong> When Hibernate initially creates tables with enum columns,
 * it may generate CHECK constraints based on the enum values present at that time.
 * If new values are added to any enum later, the old constraints will reject them.
 * This component solves that problem by recreating all constraints with current enum values.
 * </p>
 * <p>
 * <strong>Execution order:</strong> {@code @Order(0)} ensures this runs before data initialization.
 * </p>
 * <p>
 * <strong>Adding new enums:</strong> To support a new enum constraint, simply add a new entry
 * to the {@code ENUM_CONSTRAINTS} list in the constructor with the table name, column name,
 * and enum class.
 * </p>
 * <p>
 * <strong>Verifying constraints in PostgreSQL:</strong> You can check all enum CHECK constraints
 * that were created by this component using the following SQL query:
 * <pre>{@code
 * SELECT 
 *     tc.table_name,
 *     tc.constraint_name,
 *     cc.check_clause
 * FROM information_schema.table_constraints tc
 * JOIN information_schema.check_constraints cc 
 *     ON tc.constraint_name = cc.constraint_name
 * WHERE tc.constraint_type = 'CHECK'
 *     AND tc.constraint_name LIKE '%\_check'
 * ORDER BY tc.table_name, tc.constraint_name;
 * }</pre>
 * This query will display all CHECK constraints ending with '_check', allowing you to verify
 * that all enum constraints have been properly created and contain the correct values.
 * </p>
 *
 * @see SeedbedRole
 * @see Sex
 * @see TypeOfInternalUser
 * @see AcademicProgramType
 * @see LineOfResearch
 * @see TypeOfExternalUser
 * @see RoleDataInitializer
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(0)
public class EnumConstraintInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String LOG_SEPARATOR = "=======================================";

    /**
     * Configuration for all enum constraints in the database.
     * Each entry defines: table name, column name, and the corresponding enum class.
     */
    private static final List<EnumConstraintConfig> ENUM_CONSTRAINTS = List.of(
            new EnumConstraintConfig("roles", "name", SeedbedRole.class),
            new EnumConstraintConfig("users", "sex", Sex.class),
            new EnumConstraintConfig("users", "type_of_internal_user", TypeOfInternalUser.class),
            new EnumConstraintConfig("academic_programs", "academic_program_type", AcademicProgramType.class),
            new EnumConstraintConfig("research_seedbeds", "line_of_research", LineOfResearch.class),
            new EnumConstraintConfig("external_user_profiles", "type_of_external_user", TypeOfExternalUser.class),
            new EnumConstraintConfig("investigation_groups_lines_of_research", "line_of_research", LineOfResearch.class)
    );

    @Override
    @Transactional
    public void run(String... args) {
        log.info(LOG_SEPARATOR);
        log.info("Starting enum constraints update...");
        log.info(LOG_SEPARATOR);

        int successCount = 0;
        int errorCount = 0;

        for (EnumConstraintConfig config : ENUM_CONSTRAINTS) {
            try {
                updateEnumConstraint(config);
                successCount++;
                log.info("✓ Successfully updated constraint for {}.{} with {} values",
                        config.tableName, config.columnName, config.enumClass.getEnumConstants().length);
            } catch (Exception e) {
                errorCount++;
                log.error("✗ Error updating constraint for {}.{}: {}",
                        config.tableName, config.columnName, e.getMessage());
            }
        }

        log.info(LOG_SEPARATOR);
        log.info("Enum constraints update completed!");
        log.info("Success: {} | Errors: {}", successCount, errorCount);
        log.info(LOG_SEPARATOR);
    }

    /**
     * Updates the CHECK constraint for a specific enum column.
     * <p>
     * Process:
     * <ol>
     *   <li>Generates the constraint name based on table and column</li>
     *   <li>Checks if the constraint exists</li>
     *   <li>Drops the existing constraint if found</li>
     *   <li>Creates a new constraint with all current enum values</li>
     * </ol>
     * </p>
     *
     * @param config Configuration specifying the table, column, and enum class
     */
    private void updateEnumConstraint(EnumConstraintConfig config) {
        String constraintName = config.tableName + "_" + config.columnName + "_check";

        // Check if the constraint exists
        Long constraintExists = (Long) entityManager.createNativeQuery(
                """
                SELECT COUNT(*) 
                FROM information_schema.check_constraints 
                WHERE constraint_name = ?1
                """, Long.class
        ).setParameter(1, constraintName).getSingleResult();

        // Drop existing constraint if found
        if (constraintExists > 0) {
            log.debug("Dropping existing constraint: {}", constraintName);
            String dropSql = String.format(
                    "ALTER TABLE %s DROP CONSTRAINT %s",
                    config.tableName, constraintName
            );
            entityManager.createNativeQuery(dropSql).executeUpdate();
        }

        // Build constraint with all current enum values
        String enumValues = Arrays.stream(config.enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining("', '", "'", "'"));

        String createSql = String.format(
                "ALTER TABLE %s ADD CONSTRAINT %s CHECK (%s IN (%s))",
                config.tableName, constraintName, config.columnName, enumValues
        );

        entityManager.createNativeQuery(createSql).executeUpdate();
    }

    /**
     * Configuration record for an enum constraint.
     * Holds the table name, column name, and enum class for constraint management.
     */
    private record EnumConstraintConfig(
            String tableName,
            String columnName,
            Class<? extends Enum<?>> enumClass
    ) {}
}