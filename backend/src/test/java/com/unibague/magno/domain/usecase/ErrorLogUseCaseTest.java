package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.domain.spi.IErrorLogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorLogUseCaseTest {

    @Mock
    private IErrorLogPersistencePort errorLogPersistencePort;

    private ErrorLogUseCase errorLogUseCase;
    private ErrorLog errorLog;

    @BeforeEach
    void setUp() {
        errorLogUseCase = new ErrorLogUseCase(errorLogPersistencePort);

        errorLog = new ErrorLog();
        errorLog.setId(1L);
        errorLog.setUserId(1L);
        errorLog.setErrorMessage("Test error message");
        errorLog.setTimestamp(LocalDateTime.now());
    }

    @Test
    void save_ValidErrorLog_SavesSuccessfully() {
        // Arrange
        when(errorLogPersistencePort.save(errorLog)).thenReturn(errorLog);

        // Act
        ErrorLog result = errorLogUseCase.save(errorLog);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getErrorMessage()).isEqualTo("Test error message");
        verify(errorLogPersistencePort, times(1)).save(errorLog);
    }

    @Test
    void findAll_ReturnsAllErrorLogs() {
        // Arrange
        ErrorLog errorLog2 = new ErrorLog();
        errorLog2.setId(2L);
        errorLog2.setErrorMessage("Another error");

        List<ErrorLog> errorLogs = Arrays.asList(errorLog, errorLog2);
        when(errorLogPersistencePort.findAll()).thenReturn(errorLogs);

        // Act
        List<ErrorLog> result = errorLogUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getErrorMessage()).isEqualTo("Test error message");
        assertThat(result.get(1).getErrorMessage()).isEqualTo("Another error");
        verify(errorLogPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoErrorLogs_ReturnsEmptyList() {
        // Arrange
        when(errorLogPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ErrorLog> result = errorLogUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(errorLogPersistencePort, times(1)).findAll();
    }

    @Test
    void findByUserId_ReturnsUserErrorLogs() {
        // Arrange
        List<ErrorLog> userLogs = Collections.singletonList(errorLog);
        when(errorLogPersistencePort.findByUserId(1L)).thenReturn(userLogs);

        // Act
        List<ErrorLog> result = errorLogUseCase.findByUserId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(errorLogPersistencePort, times(1)).findByUserId(1L);
    }

    @Test
    void findByUserId_UserHasNoLogs_ReturnsEmptyList() {
        // Arrange
        when(errorLogPersistencePort.findByUserId(99L)).thenReturn(Collections.emptyList());

        // Act
        List<ErrorLog> result = errorLogUseCase.findByUserId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(errorLogPersistencePort, times(1)).findByUserId(99L);
    }

    @Test
    void findByDateRange_ReturnsLogsInRange() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59);

        errorLog.setTimestamp(LocalDateTime.of(2024, 6, 15, 10, 30));
        List<ErrorLog> logsInRange = Arrays.asList(errorLog);

        when(errorLogPersistencePort.findByTimestampBetween(start, end))
                .thenReturn(logsInRange);

        // Act
        List<ErrorLog> result = errorLogUseCase.findByDateRange(start, end);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTimestamp()).isBefore(end);
        assertThat(result.getFirst().getTimestamp()).isAfter(start);
        verify(errorLogPersistencePort, times(1)).findByTimestampBetween(start, end);
    }

    @Test
    void findByDateRange_NoLogsInRange_ReturnsEmptyList() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(errorLogPersistencePort.findByTimestampBetween(start, end))
                .thenReturn(Collections.emptyList());

        // Act
        List<ErrorLog> result = errorLogUseCase.findByDateRange(start, end);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(errorLogPersistencePort, times(1)).findByTimestampBetween(start, end);
    }

    @Test
    void getLogsOlderThanDays_WithLocalDateTime_ReturnsOldLogs() {
        // Arrange
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        errorLog.setTimestamp(LocalDateTime.now().minusDays(35));

        List<ErrorLog> oldLogs = Collections.singletonList(errorLog);
        when(errorLogPersistencePort.getLogsOlderThanDays(cutoffDate))
                .thenReturn(oldLogs);

        // Act
        List<ErrorLog> result = errorLogUseCase.getLogsOlderThanDays(cutoffDate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(cutoffDate);
    }

    @Test
    void getLogsOlderThanDays_WithLocalDateTime_NoOldLogs_ReturnsEmptyList() {
        // Arrange
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);

        when(errorLogPersistencePort.getLogsOlderThanDays(cutoffDate))
                .thenReturn(Collections.emptyList());

        // Act
        List<ErrorLog> result = errorLogUseCase.getLogsOlderThanDays(cutoffDate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(cutoffDate);
    }

    @Test
    void getLogsOlderThanDays_WithIntDays_ReturnsOldLogs() {
        // Arrange
        int days = 30;
        errorLog.setTimestamp(LocalDateTime.now().minusDays(35));

        List<ErrorLog> oldLogs = Collections.singletonList(errorLog);
        when(errorLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(oldLogs);

        // Act
        List<ErrorLog> result = errorLogUseCase.getLogsOlderThanDays(days);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(any(LocalDateTime.class));
    }

    @Test
    void getLogsOlderThanDays_WithIntDays_NoOldLogs_ReturnsEmptyList() {
        // Arrange
        int days = 30;

        when(errorLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<ErrorLog> result = errorLogUseCase.getLogsOlderThanDays(days);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(any(LocalDateTime.class));
    }

    @Test
    void deleteLogsOlderThanDays_HasOldLogs_DeletesSuccessfully() {
        // Arrange
        int days = 30;

        ErrorLog oldLog1 = new ErrorLog();
        oldLog1.setId(1L);
        oldLog1.setTimestamp(LocalDateTime.now().minusDays(35));

        ErrorLog oldLog2 = new ErrorLog();
        oldLog2.setId(2L);
        oldLog2.setTimestamp(LocalDateTime.now().minusDays(40));

        List<ErrorLog> oldLogs = Arrays.asList(oldLog1, oldLog2);
        when(errorLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(oldLogs);

        // Act
        errorLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(any(LocalDateTime.class));
        verify(errorLogPersistencePort, times(1)).deleteByIds(Arrays.asList(1L, 2L));
    }

    @Test
    void deleteLogsOlderThanDays_NoOldLogs_DoesNotDeleteAnything() {
        // Arrange
        int days = 30;

        when(errorLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        errorLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(any(LocalDateTime.class));
        verify(errorLogPersistencePort, never()).deleteByIds(anyList());
    }

    @Test
    void deleteLogsOlderThanDays_SingleOldLog_DeletesSingleLog() {
        // Arrange
        int days = 30;

        ErrorLog oldLog = new ErrorLog();
        oldLog.setId(1L);
        oldLog.setTimestamp(LocalDateTime.now().minusDays(35));

        List<ErrorLog> oldLogs = List.of(oldLog);
        when(errorLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(oldLogs);

        // Act
        errorLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(errorLogPersistencePort, times(1)).getLogsOlderThanDays(any(LocalDateTime.class));
        verify(errorLogPersistencePort, times(1)).deleteByIds(List.of(1L));
    }
}