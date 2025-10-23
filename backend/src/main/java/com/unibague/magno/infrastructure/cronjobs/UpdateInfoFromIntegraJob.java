package com.unibague.magno.infrastructure.cronjobs;

import com.unibague.magno.domain.api.ICronJobExecutionLogServicePort;
import com.unibague.magno.domain.api.cronjobs.ICronJobServicePort;
import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.infrastructure.util.CronJobLogContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInfoFromIntegraJob {

    private final ICronJobServicePort cronJobServicePort;
    private final ICronJobExecutionLogServicePort cronJobExecutionLogServicePort;
    private final CronJobLogContextService cronJobLogContextService;

    // This cron job is scheduled to run every Sunday at midnight (00:00)
    @Scheduled(cron = "0 0 0 * * 7", zone = "America/Bogota")
    public void execute() {
        CronJobExecutionLog executionLog = cronJobLogContextService.createCronJobExecutionLog("UpdateInfoFromIntegraJob");
        
        try {
            cronJobServicePort.updateInfoFromIntegra();
            
            cronJobLogContextService.finalizeExecutionLog(
                executionLog, 
                "SUCCESS", 
                "Successfully synchronized data from Integra system",
                null,
                null
            );
            
        } catch (Exception e) {
            cronJobLogContextService.finalizeExecutionLog(
                executionLog, 
                "FAILED", 
                "Failed to synchronize data from Integra system",
                0,
                e
            );
        } finally {
            cronJobExecutionLogServicePort.save(executionLog);
        }
    }
}
