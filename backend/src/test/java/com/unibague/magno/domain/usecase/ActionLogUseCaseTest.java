package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.domain.spi.IActionLogPersistencePort;
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
class ActionLogUseCaseTest {

    @Mock
    private IActionLogPersistencePort actionLogPersistencePort;

    private ActionLogUseCase actionLogUseCase;
    private ActionLog actionLog;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        actionLogUseCase = new ActionLogUseCase(actionLogPersistencePort);
        now = LocalDateTime.now();

        actionLog = new ActionLog();
        actionLog.setId(1L);
        actionLog.setHttpMethod("POST");
        actionLog.setRequestUrl("/api/v1/users/login");
        actionLog.setRequestBody("{\"email\":\"user@example.com\",\"password\":\"***\"}");
        actionLog.setResponseStatus(200);
        actionLog.setResponseBody("{\"success\":true,\"token\":\"abc123\"}");
        actionLog.setTimestamp(now);
        actionLog.setUserEmail("user@example.com");
        actionLog.setUserId(100L);
        actionLog.setClientIp("192.168.1.1");
        actionLog.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        actionLog.setSessionId("session-123-abc");
        actionLog.setExecutionTimeMs(250L);
    }

    @Test
    void save_ValidActionLog_SavesSuccessfully() {
        // Arrange
        when(actionLogPersistencePort.save(actionLog)).thenReturn(actionLog);

        // Act
        ActionLog result = actionLogUseCase.save(actionLog);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getHttpMethod()).isEqualTo("POST");
        assertThat(result.getRequestUrl()).isEqualTo("/api/v1/users/login");
        assertThat(result.getRequestBody()).isEqualTo("{\"email\":\"user@example.com\",\"password\":\"***\"}");
        assertThat(result.getResponseStatus()).isEqualTo(200);
        assertThat(result.getResponseBody()).isEqualTo("{\"success\":true,\"token\":\"abc123\"}");
        assertThat(result.getTimestamp()).isEqualTo(now);
        assertThat(result.getUserEmail()).isEqualTo("user@example.com");
        assertThat(result.getUserId()).isEqualTo(100L);
        assertThat(result.getClientIp()).isEqualTo("192.168.1.1");
        assertThat(result.getUserAgent()).isEqualTo("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        assertThat(result.getSessionId()).isEqualTo("session-123-abc");
        assertThat(result.getExecutionTimeMs()).isEqualTo(250L);
        verify(actionLogPersistencePort, times(1)).save(actionLog);
    }

    @Test
    void findAll_ReturnsAllActionLogs() {
        // Arrange
        ActionLog log2 = new ActionLog();
        log2.setId(2L);
        log2.setHttpMethod("GET");
        log2.setRequestUrl("/api/v1/users/profile");
        log2.setResponseStatus(200);
        log2.setTimestamp(now.plusMinutes(5));
        log2.setUserId(101L);
        log2.setUserEmail("admin@example.com");
        log2.setClientIp("192.168.1.2");
        log2.setUserAgent("Chrome/91.0");
        log2.setSessionId("session-456-def");
        log2.setExecutionTimeMs(150L);

        List<ActionLog> logs = Arrays.asList(actionLog, log2);
        when(actionLogPersistencePort.findAll()).thenReturn(logs);

        // Act
        List<ActionLog> result = actionLogUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getHttpMethod()).isEqualTo("POST");
        assertThat(result.get(0).getUserId()).isEqualTo(100L);
        assertThat(result.get(1).getHttpMethod()).isEqualTo("GET");
        assertThat(result.get(1).getUserId()).isEqualTo(101L);
        verify(actionLogPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoLogs_ReturnsEmptyList() {
        // Arrange
        when(actionLogPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ActionLog> result = actionLogUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(actionLogPersistencePort, times(1)).findAll();
    }

    @Test
    void findByUserId_UserIdExists_ReturnsUserLogs() {
        // Arrange
        ActionLog log2 = new ActionLog();
        log2.setId(2L);
        log2.setHttpMethod("PUT");
        log2.setRequestUrl("/api/v1/users/100/profile");
        log2.setResponseStatus(200);
        log2.setTimestamp(now.plusMinutes(10));
        log2.setUserId(100L);
        log2.setUserEmail("user@example.com");
        log2.setClientIp("192.168.1.1");
        log2.setSessionId("session-123-abc");
        log2.setExecutionTimeMs(180L);

        List<ActionLog> logs = Arrays.asList(actionLog, log2);
        when(actionLogPersistencePort.findByUserId(100L)).thenReturn(logs);

        // Act
        List<ActionLog> result = actionLogUseCase.findByUserId(100L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUserId()).isEqualTo(100L);
        assertThat(result.get(0).getHttpMethod()).isEqualTo("POST");
        assertThat(result.get(1).getUserId()).isEqualTo(100L);
        assertThat(result.get(1).getHttpMethod()).isEqualTo("PUT");
        verify(actionLogPersistencePort, times(1)).findByUserId(100L);
    }

    @Test
    void findByUserId_UserIdDoesNotExist_ReturnsEmptyList() {
        // Arrange
        when(actionLogPersistencePort.findByUserId(999L)).thenReturn(Collections.emptyList());

        // Act
        List<ActionLog> result = actionLogUseCase.findByUserId(999L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(actionLogPersistencePort, times(1)).findByUserId(999L);
    }

    @Test
    void findByDateRange_ValidDateRange_ReturnsLogs() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 2, 7, 23, 59);

        ActionLog log2 = new ActionLog();
        log2.setId(2L);
        log2.setHttpMethod("DELETE");
        log2.setRequestUrl("/api/v1/users/50");
        log2.setResponseStatus(204);
        log2.setTimestamp(LocalDateTime.of(2025, 2, 5, 14, 30));
        log2.setUserId(200L);
        log2.setUserEmail("admin@example.com");
        log2.setClientIp("10.0.0.1");
        log2.setExecutionTimeMs(320L);

        List<ActionLog> logs = Arrays.asList(actionLog, log2);
        when(actionLogPersistencePort.findByTimestampBetween(start, end))
                .thenReturn(logs);

        // Act
        List<ActionLog> result = actionLogUseCase.findByDateRange(start, end);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getHttpMethod()).isEqualTo("POST");
        assertThat(result.get(1).getHttpMethod()).isEqualTo("DELETE");
        verify(actionLogPersistencePort, times(1)).findByTimestampBetween(start, end);
    }

    @Test
    void findByDateRange_NoLogsInRange_ReturnsEmptyList() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 31, 23, 59);

        when(actionLogPersistencePort.findByTimestampBetween(start, end))
                .thenReturn(Collections.emptyList());

        // Act
        List<ActionLog> result = actionLogUseCase.findByDateRange(start, end);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(actionLogPersistencePort, times(1)).findByTimestampBetween(start, end);
    }

    @Test
    void getLogsOlderThanDays_LogsExist_ReturnsOldLogs() {
        // Arrange
        LocalDateTime date = LocalDateTime.now().minusDays(30);

        ActionLog oldLog = new ActionLog();
        oldLog.setId(3L);
        oldLog.setHttpMethod("GET");
        oldLog.setRequestUrl("/api/v1/old-endpoint");
        oldLog.setResponseStatus(200);
        oldLog.setTimestamp(LocalDateTime.now().minusDays(35));
        oldLog.setUserId(150L);
        oldLog.setUserEmail("olduser@example.com");
        oldLog.setClientIp("172.16.0.1");
        oldLog.setUserAgent("Safari/14.0");
        oldLog.setSessionId("old-session-789");
        oldLog.setExecutionTimeMs(500L);

        List<ActionLog> oldLogs = List.of(oldLog);
        when(actionLogPersistencePort.getLogsOlderThanDays(date)).thenReturn(oldLogs);

        // Act
        List<ActionLog> result = actionLogUseCase.getLogsOlderThanDays(date);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(3L);
        assertThat(result.getFirst().getHttpMethod()).isEqualTo("GET");
        assertThat(result.getFirst().getUserId()).isEqualTo(150L);
        assertThat(result.getFirst().getExecutionTimeMs()).isEqualTo(500L);
        verify(actionLogPersistencePort, times(1)).getLogsOlderThanDays(date);
    }

    @Test
    void getLogsOlderThanDays_NoOldLogs_ReturnsEmptyList() {
        // Arrange
        LocalDateTime date = LocalDateTime.now().minusDays(30);
        when(actionLogPersistencePort.getLogsOlderThanDays(date))
                .thenReturn(Collections.emptyList());

        // Act
        List<ActionLog> result = actionLogUseCase.getLogsOlderThanDays(date);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(actionLogPersistencePort, times(1)).getLogsOlderThanDays(date);
    }

    @Test
    void deleteLogsOlderThanDays_OldLogsExist_DeletesSuccessfully() {
        // Arrange
        int days = 30;

        ActionLog oldLog1 = new ActionLog();
        oldLog1.setId(10L);
        oldLog1.setHttpMethod("POST");
        oldLog1.setRequestUrl("/api/v1/test");
        oldLog1.setResponseStatus(500);
        oldLog1.setTimestamp(LocalDateTime.now().minusDays(35));
        oldLog1.setUserId(300L);
        oldLog1.setExecutionTimeMs(1000L);

        ActionLog oldLog2 = new ActionLog();
        oldLog2.setId(11L);
        oldLog2.setHttpMethod("GET");
        oldLog2.setRequestUrl("/api/v1/legacy");
        oldLog2.setResponseStatus(404);
        oldLog2.setTimestamp(LocalDateTime.now().minusDays(40));
        oldLog2.setUserId(301L);
        oldLog2.setExecutionTimeMs(750L);

        List<ActionLog> oldLogs = Arrays.asList(oldLog1, oldLog2);
        when(actionLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(oldLogs);

        // Act
        actionLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(actionLogPersistencePort, times(1))
                .getLogsOlderThanDays(any(LocalDateTime.class));
        verify(actionLogPersistencePort, times(1))
                .deleteByIds(Arrays.asList(10L, 11L));
    }

    @Test
    void deleteLogsOlderThanDays_NoOldLogs_DoesNotDelete() {
        // Arrange
        int days = 30;
        when(actionLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        actionLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(actionLogPersistencePort, times(1))
                .getLogsOlderThanDays(any(LocalDateTime.class));
        verify(actionLogPersistencePort, never()).deleteByIds(anyList());
    }

    @Test
    void deleteLogsOlderThanDays_ZeroDays_DeletesTodayAndOlder() {
        // Arrange
        int days = 0;

        ActionLog oldLog = new ActionLog();
        oldLog.setId(12L);
        oldLog.setHttpMethod("PATCH");
        oldLog.setRequestUrl("/api/v1/update");
        oldLog.setResponseStatus(200);
        oldLog.setTimestamp(LocalDateTime.now().minusHours(1));
        oldLog.setUserId(400L);
        oldLog.setUserEmail("test@example.com");
        oldLog.setClientIp("127.0.0.1");
        oldLog.setExecutionTimeMs(100L);

        List<ActionLog> oldLogs = List.of(oldLog);
        when(actionLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(oldLogs);

        // Act
        actionLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(actionLogPersistencePort, times(1))
                .getLogsOlderThanDays(any(LocalDateTime.class));
        verify(actionLogPersistencePort, times(1))
                .deleteByIds(List.of(12L));
    }

    @Test
    void deleteLogsOlderThanDays_MultipleOldLogsWithDifferentMethods_DeletesAll() {
        // Arrange
        int days = 60;

        ActionLog log1 = new ActionLog();
        log1.setId(20L);
        log1.setHttpMethod("POST");
        log1.setResponseStatus(201);
        log1.setTimestamp(LocalDateTime.now().minusDays(65));

        ActionLog log2 = new ActionLog();
        log2.setId(21L);
        log2.setHttpMethod("PUT");
        log2.setResponseStatus(200);
        log2.setTimestamp(LocalDateTime.now().minusDays(70));

        ActionLog log3 = new ActionLog();
        log3.setId(22L);
        log3.setHttpMethod("DELETE");
        log3.setResponseStatus(204);
        log3.setTimestamp(LocalDateTime.now().minusDays(80));

        List<ActionLog> oldLogs = Arrays.asList(log1, log2, log3);
        when(actionLogPersistencePort.getLogsOlderThanDays(any(LocalDateTime.class)))
                .thenReturn(oldLogs);

        // Act
        actionLogUseCase.deleteLogsOlderThanDays(days);

        // Assert
        verify(actionLogPersistencePort, times(1))
                .getLogsOlderThanDays(any(LocalDateTime.class));
        verify(actionLogPersistencePort, times(1))
                .deleteByIds(Arrays.asList(20L, 21L, 22L));
    }
}