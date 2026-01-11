package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Automatically updates the CHECK constraint on the 'roles' table at application startup.
 * <p>
 * This component ensures that the database constraint stays synchronized with the
 * {@link SeedbedRole} enum values. It runs before {@link RoleDataInitializer} to guarantee
 * that all enum roles can be inserted without constraint violations.
 * </p>
 * <p>
 * <strong>Why this is needed:</strong> When Hibernate initially creates the 'roles' table,
 * it may generate a CHECK constraint based on the enum values present at that time.
 * If new roles are added to the enum later, the old constraint will reject them.
 * This component solves that problem by recreating the constraint with all current enum values.
 * </p>
 * <p>
 * <strong>Execution order:</strong> {@code @Order(0)} ensures this runs before role data initialization.
 * </p>
 *
 * @see SeedbedRole
 * @see RoleDataInitializer
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(0)
public class RoleConstraintFixer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("---------------------------------------");
        log.info("Checking roles table constraint...");

        try {
            updateRoleConstraint();
            log.info("Constraint updated successfully with {} roles", SeedbedRole.values().length);
        } catch (Exception e) {
            log.error("Error managing constraint: {}", e.getMessage());
        }

        log.info("---------------------------------------");
    }

    /**
     * Updates the CHECK constraint on the 'roles' table to match current enum values.
     * <p>
     * Process:
     * <ol>
     *   <li>Checks if the constraint exists</li>
     *   <li>Drops the existing constraint if found</li>
     *   <li>Creates a new constraint with all current {@link SeedbedRole} values</li>
     * </ol>
     * </p>
     */
    private void updateRoleConstraint() {
        // Check if the constraint exists
        Long constraintExists = (Long) entityManager.createNativeQuery(
                """
                SELECT COUNT(*) 
                FROM information_schema.check_constraints 
                WHERE constraint_name = 'roles_name_check'
                """, Long.class
        ).getSingleResult();

        if (constraintExists > 0) {
            log.info("Constraint exists, removing...");
            entityManager.createNativeQuery(
                    "ALTER TABLE roles DROP CONSTRAINT roles_name_check"
            ).executeUpdate();
        }

        // Build constraint with all current enum values
        String rolesIn = Arrays.stream(SeedbedRole.values())
                .map(Enum::name)
                .collect(Collectors.joining("', '", "'", "'"));

        String sql = String.format(
                "ALTER TABLE roles ADD CONSTRAINT roles_name_check CHECK (name IN (%s))",
                rolesIn
        );

        entityManager.createNativeQuery(sql).executeUpdate();
    }
}