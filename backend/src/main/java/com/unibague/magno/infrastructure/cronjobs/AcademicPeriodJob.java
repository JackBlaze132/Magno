package com.unibague.magno.infrastructure.cronjobs;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.ICronJobExecutionLogServicePort;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import com.unibague.magno.infrastructure.util.CronJobLogContextService;
import com.unibague.magno.infrastructure.util.CronJobLogFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Scheduled job for managing academic period lifecycle.
 * Automatically deactivates academic periods that have passed their end date,
 * ensuring only current periods remain active in the system.
 * Runs daily at 2:00 AM (America/Bogota timezone).
 */
@Component
@RequiredArgsConstructor
public class AcademicPeriodJob {

    private static final int DAYS_THRESHOLD = 30;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final IAcademicPeriodPersistencePort academicPeriodPersistencePort;
    private final ICronJobExecutionLogServicePort cronJobExecutionLogServicePort;
    private final CronJobLogContextService cronJobLogContextService;
    private final CronJobLogFileService cronJobLogFileService;

    // This cron job is scheduled to run every day at midnight (02:00)
    @Scheduled(cron = "0 0 2 * * *", zone = "America/Bogota")
    public void execute() {
        CronJobExecutionLog executionLog = cronJobLogContextService.createCronJobExecutionLog("AcademicPeriodJob");
        cronJobLogFileService.generateLogFile("AcademicPeriodJob", DAYS_THRESHOLD);
        
        try {
            List<AcademicPeriod> allPeriods = academicPeriodServicePort.findAll();
            LocalDate today = LocalDate.now();
            int updatedCount = 0;
            
            for (AcademicPeriod period : allPeriods) {
                // Check if the period's end date has passed and it's still marked as current
                if (period.isCurrent() && period.getEndDate().isBefore(today)) {
                    // Create a new AcademicPeriod with isCurrent set to false
                    AcademicPeriod updatedPeriod = new AcademicPeriod(
                        period.getId(),
                        period.getName(),
                        period.getStartDate(),
                        period.getEndDate(),
                            false, true
                    );
                    // Use the persistence port directly to bypass validation that prevents updating inactive periods
                    // This is acceptable for system/cron jobs that need to deactivate expired periods
                    academicPeriodPersistencePort.update(period.getId(), updatedPeriod);
                    updatedCount++;
                }
            }
            
            cronJobLogContextService.finalizeExecutionLog(
                executionLog, 
                "SUCCESS", 
                "Successfully updated " + updatedCount + " academic period(s) that have passed their end date",
                updatedCount,
                null
            );
            
        } catch (Exception e) {
            cronJobLogContextService.finalizeExecutionLog(
                executionLog, 
                "FAILED", 
                "Failed to update expired academic periods",
                0,
                e
            );
        } finally {
            cronJobExecutionLogServicePort.save(executionLog);
        }
    }
}
