package com.unibague.magno.domain.api.cronjobs;

/**
 * Service port interface that defines the contract for scheduled cron job operations.
 * <p>
 * This interface provides methods for executing scheduled tasks such as synchronizing
 * data from external systems and performing maintenance operations like log cleanup.
 * </p>
 */
public interface ICronJobServicePort {
    
    /**
     * Updates information from the Integra external system.
     * <p>
     * This scheduled task synchronizes student and functionary data from the
     * institutional Integra system, ensuring the application has up-to-date information.
     * </p>
     */
    void updateInfoFromIntegra();
    
    /**
     * Deletes old error logs based on age threshold.
     * <p>
     * This maintenance task removes error logs that are older than the specified
     * number of days to prevent database bloat and maintain performance.
     * </p>
     *
     * @param days the age threshold in days for deleting error logs
     */
    void deleteOldErrorLogs(int days);
}
