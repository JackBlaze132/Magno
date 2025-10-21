package com.unibague.magno.infrastructure.cronjobs;

import com.unibague.magno.domain.api.IErrorLogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorLogJob {

    private static final int DAYS_THRESHOLD = 30;

    private final IErrorLogServicePort errorLogServicePort;

    // This cron job is scheduled to run every Sunday at (01:00)
    @Scheduled(cron = "0 0 1 * * 7", zone = "America/Bogota")
    public void execute() {
        errorLogServicePort.deleteLogsOlderThanDays(DAYS_THRESHOLD);
    }
}
