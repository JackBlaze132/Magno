package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.api.cronjobs.ICronJobServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class UserDataInitializer implements CommandLineRunner {

    private final ICronJobServicePort cronJobServicePort;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n---------------------------------------\n");
        cronJobServicePort.updateInfoFromIntegra();
        log.info("\n\nUser data initialization completed.");
        log.info("\n---------------------------------------\n");
    }

}
