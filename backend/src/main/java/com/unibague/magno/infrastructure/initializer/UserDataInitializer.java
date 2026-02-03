package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.api.cronjobs.ICronJobServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Initializer responsible for synchronizing user data from the external Integra system
 * at application startup.
 * <p>
 * This component triggers the update process to fetch and synchronize academic programs,
 * dependencies, and user information (functionaries and students) from the university's
 * central Integra system into Magno.
 * </p>
 * <p>
 * <strong>Execution order:</strong> {@code @Order(2)} - Runs after {@link RoleDataInitializer}
 * to ensure roles exist before user data is synchronized.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
@Profile("!test")
public class UserDataInitializer implements CommandLineRunner {

    private final ICronJobServicePort cronJobServicePort;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n\n\n---------------------------------------\n\n\n");
        cronJobServicePort.updateInfoFromIntegra();
        log.info("\n\n\nUser data initialization completed.\n\n\n");
        log.info("\n\n\n---------------------------------------\n\n\n");
    }

}
