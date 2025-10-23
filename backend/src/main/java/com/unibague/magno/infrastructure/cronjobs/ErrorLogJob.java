package com.unibague.magno.infrastructure.cronjobs;

import com.unibague.magno.domain.api.ICronJobExecutionLogServicePort;
import com.unibague.magno.domain.api.IErrorLogServicePort;
import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.infrastructure.util.CronJobLogContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ErrorLogJob {

    private static final int DAYS_THRESHOLD = 30;

    private final IErrorLogServicePort errorLogServicePort;
    private final ICronJobExecutionLogServicePort cronJobExecutionLogServicePort;
    private final CronJobLogContextService cronJobLogContextService;

    // This cron job is scheduled to run every Sunday at (01:00)
    @Scheduled(cron = "0 0 1 * * 7", zone = "America/Bogota")
    public void execute() {
        CronJobExecutionLog executionLog = cronJobLogContextService.createCronJobExecutionLog("ErrorLogJob");
        
        try {
            // Get old logs to count how many will be deleted
            List<ErrorLog> oldLogs = errorLogServicePort.getLogsOlderThanDays(DAYS_THRESHOLD);
            int deletedCount = oldLogs.size();
            
            // Delete old logs
            errorLogServicePort.deleteLogsOlderThanDays(DAYS_THRESHOLD);
            
            cronJobLogContextService.finalizeExecutionLog(
                executionLog, 
                "SUCCESS", 
                "Successfully deleted " + deletedCount + " old error logs older than " + DAYS_THRESHOLD + " days",
                deletedCount,
                null
            );
            
        } catch (Exception e) {
            cronJobLogContextService.finalizeExecutionLog(
                executionLog, 
                "FAILED", 
                "Failed to delete old error logs",
                0,
                e
            );
        } finally {
            cronJobExecutionLogServicePort.save(executionLog);
        }
    }
}
