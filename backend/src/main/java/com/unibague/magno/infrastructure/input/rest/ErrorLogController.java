package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.response.ErrorLogResponse;
import com.unibague.magno.application.handler.impl.ErrorLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/error-logs")
public class ErrorLogController {

    private final ErrorLogHandler errorLogHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ErrorLogResponse>> getAllErrorLogs() {
        List<ErrorLogResponse> errorLogs = errorLogHandler.findAll();
        return ResponseEntity.ok(errorLogs);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/by-user-id", headers = "API-VERSION=1")
    public ResponseEntity<List<ErrorLogResponse>> getErrorLogsByUserId(@RequestParam Long userId) {
        List<ErrorLogResponse> errorLogs = errorLogHandler.findByUserId(userId);
        return ResponseEntity.ok(errorLogs);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/by-date-range", headers = "API-VERSION=1")
    public ResponseEntity<List<ErrorLogResponse>> getErrorLogsByDateRange(@RequestParam String start, @RequestParam String end) {
        List<ErrorLogResponse> errorLogs = errorLogHandler.findByTimestampBetween(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
        return ResponseEntity.ok(errorLogs);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/older-than", headers = "API-VERSION=1")
    public ResponseEntity<List<ErrorLogResponse>> getErrorLogsOlderThanDays(@RequestParam String date) {
        List<ErrorLogResponse> errorLogs = errorLogHandler.getLogsOlderThanDays(
                LocalDateTime.parse(date)
        );
        return ResponseEntity.ok(errorLogs);
    }
}

