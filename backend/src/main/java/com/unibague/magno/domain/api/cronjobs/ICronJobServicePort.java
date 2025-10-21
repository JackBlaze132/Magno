package com.unibague.magno.domain.api.cronjobs;

public interface ICronJobServicePort {
    void updateInfoFromIntegra();
    void deleteOldErrorLogs(int days);
}
