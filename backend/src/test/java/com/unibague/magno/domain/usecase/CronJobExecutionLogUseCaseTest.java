package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.domain.spi.ICronJobExecutionLogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CronJobExecutionLogUseCaseTest {

    @Mock
    private ICronJobExecutionLogPersistencePort cronJobExecutionLogPersistencePort;

    private CronJobExecutionLogUseCase cronJobExecutionLogUseCase;
    private CronJobExecutionLog cronJobExecutionLog;

    @BeforeEach
    void setUp() {
        cronJobExecutionLogUseCase = new CronJobExecutionLogUseCase(
                cronJobExecutionLogPersistencePort
        );

        cronJobExecutionLog = new CronJobExecutionLog();
        cronJobExecutionLog.setId(1L);
        cronJobExecutionLog.setJobName("SyncDataJob");
        cronJobExecutionLog.setStatus("SUCCESS");
        cronJobExecutionLog.setStartTime(LocalDateTime.now());
        cronJobExecutionLog.setEndTime(LocalDateTime.now().plusMinutes(5));
    }

    @Test
    void save_ValidCronJobExecutionLog_SavesSuccessfully() {
        // Arrange
        when(cronJobExecutionLogPersistencePort.save(cronJobExecutionLog))
                .thenReturn(cronJobExecutionLog);

        // Act
        CronJobExecutionLog result = cronJobExecutionLogUseCase.save(cronJobExecutionLog);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getJobName()).isEqualTo("SyncDataJob");
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
        verify(cronJobExecutionLogPersistencePort, times(1)).save(cronJobExecutionLog);
    }

    @Test
    void findAll_ReturnsAllCronJobExecutionLogs() {
        // Arrange
        CronJobExecutionLog log2 = new CronJobExecutionLog();
        log2.setId(2L);
        log2.setJobName("CleanupJob");
        log2.setStatus("FAILED");

        List<CronJobExecutionLog> logs = Arrays.asList(cronJobExecutionLog, log2);
        when(cronJobExecutionLogPersistencePort.findAll()).thenReturn(logs);

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getJobName()).isEqualTo("SyncDataJob");
        assertThat(result.get(1).getJobName()).isEqualTo("CleanupJob");
        verify(cronJobExecutionLogPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoLogs_ReturnsEmptyList() {
        // Arrange
        when(cronJobExecutionLogPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(cronJobExecutionLogPersistencePort, times(1)).findAll();
    }

    @Test
    void findByJobName_JobNameExists_ReturnsLogs() {
        // Arrange
        List<CronJobExecutionLog> logs = Arrays.asList(cronJobExecutionLog);
        when(cronJobExecutionLogPersistencePort.findByJobName("SyncDataJob"))
                .thenReturn(logs);

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findByJobName("SyncDataJob");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getJobName()).isEqualTo("SyncDataJob");
        verify(cronJobExecutionLogPersistencePort, times(1)).findByJobName("SyncDataJob");
    }

    @Test
    void findByJobName_JobNameDoesNotExist_ReturnsEmptyList() {
        // Arrange
        when(cronJobExecutionLogPersistencePort.findByJobName("NonExistentJob"))
                .thenReturn(Collections.emptyList());

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findByJobName("NonExistentJob");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(cronJobExecutionLogPersistencePort, times(1)).findByJobName("NonExistentJob");
    }

    @Test
    void findByStatus_StatusExists_ReturnsLogs() {
        // Arrange
        List<CronJobExecutionLog> logs = Arrays.asList(cronJobExecutionLog);
        when(cronJobExecutionLogPersistencePort.findByStatus("SUCCESS"))
                .thenReturn(logs);

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findByStatus("SUCCESS");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("SUCCESS");
        verify(cronJobExecutionLogPersistencePort, times(1)).findByStatus("SUCCESS");
    }

    @Test
    void findByStatus_StatusDoesNotExist_ReturnsEmptyList() {
        // Arrange
        when(cronJobExecutionLogPersistencePort.findByStatus("UNKNOWN"))
                .thenReturn(Collections.emptyList());

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findByStatus("UNKNOWN");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(cronJobExecutionLogPersistencePort, times(1)).findByStatus("UNKNOWN");
    }

    @Test
    void findByDateRange_ValidDateRange_ReturnsLogs() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 31, 23, 59);
        List<CronJobExecutionLog> logs = Arrays.asList(cronJobExecutionLog);

        when(cronJobExecutionLogPersistencePort.findByDateRange(startDate, endDate))
                .thenReturn(logs);

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findByDateRange(startDate, endDate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(cronJobExecutionLogPersistencePort, times(1)).findByDateRange(startDate, endDate);
    }

    @Test
    void findByDateRange_NoLogsInRange_ReturnsEmptyList() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59);

        when(cronJobExecutionLogPersistencePort.findByDateRange(startDate, endDate))
                .thenReturn(Collections.emptyList());

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findByDateRange(startDate, endDate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(cronJobExecutionLogPersistencePort, times(1)).findByDateRange(startDate, endDate);
    }

    @Test
    void findRecentExecutions_ValidLimit_ReturnsLimitedLogs() {
        // Arrange
        CronJobExecutionLog log2 = new CronJobExecutionLog();
        log2.setId(2L);
        log2.setJobName("CleanupJob");

        CronJobExecutionLog log3 = new CronJobExecutionLog();
        log3.setId(3L);
        log3.setJobName("ReportJob");

        List<CronJobExecutionLog> logs = Arrays.asList(cronJobExecutionLog, log2, log3);
        when(cronJobExecutionLogPersistencePort.findRecentExecutions(10))
                .thenReturn(logs);

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findRecentExecutions(10);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        verify(cronJobExecutionLogPersistencePort, times(1)).findRecentExecutions(10);
    }

    @Test
    void findRecentExecutions_NoExecutions_ReturnsEmptyList() {
        // Arrange
        when(cronJobExecutionLogPersistencePort.findRecentExecutions(10))
                .thenReturn(Collections.emptyList());

        // Act
        List<CronJobExecutionLog> result = cronJobExecutionLogUseCase.findRecentExecutions(10);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(cronJobExecutionLogPersistencePort, times(1)).findRecentExecutions(10);
    }

    @Test
    void deleteLogsOlderThanDays_ValidDays_DeletesSuccessfully() {
        // Arrange
        int days = 30;

        // Act
        cronJobExecutionLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(cronJobExecutionLogPersistencePort, times(1)).deleteLogsOlderThanDays(days);
    }

    @Test
    void deleteLogsOlderThanDays_ZeroDays_DeletesSuccessfully() {
        // Arrange
        int days = 0;

        // Act
        cronJobExecutionLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(cronJobExecutionLogPersistencePort, times(1)).deleteLogsOlderThanDays(days);
    }
}