package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Initializer responsible for populating the database with all seedbed roles
 * defined in the {@link SeedbedRole} enum at application startup.
 * <p>
 * This component checks for the existence of each role and creates any missing ones,
 * ensuring the role table is always synchronized with the enum values.
 * </p>
 * <p>
 * <strong>Execution order:</strong> {@code @Order(1)} - Runs after {@link EnumConstraintInitializer}
 * to ensure database constraints are updated before inserting roles.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(1)
public class RoleDataInitializer implements CommandLineRunner {

    private final IRoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {

        log.info("\n\n\n---------------------------------------\n\n\n");
        log.info("Initializing seedbed roles...");
        Arrays.stream(SeedbedRole.values()).forEach(seedbedRole -> {
            if (!roleRepository.existsByName(seedbedRole)) {
                RoleEntity role = RoleEntity.builder()
                        .name(seedbedRole)
                        .description(seedbedRole.getDescription())
                        .build();

                log.info("Creating role: {}", seedbedRole);
                roleRepository.save(role);
            }
        });
        log.info("\n\n\nSeedbed roles initialization completed.\n\n\n");
        log.info("\n\n\n---------------------------------------\n\n\n");
    }
}
