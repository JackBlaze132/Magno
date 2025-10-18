package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    private final IRoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {

        log.info("---------------------------------------\n");
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
        log.info("\n\nSeedbed roles initialization completed.");
        log.info("---------------------------------------\n");
    }
}
