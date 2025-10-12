package com.unibague.magno.infrastructure.cronjobs;

import com.unibague.magno.domain.api.cronjobs.ICronJobServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInfoFromIntegraJob {

    private final ICronJobServicePort cronJobServicePort;

    // This cron job is scheduled to run every Sunday at midnight (00:00)
    @Scheduled(cron = "0 0 0 * * 7", zone = "America/Bogota")
    public void execute() {
        cronJobServicePort.updateInfoFromIntegra();
    }
}
