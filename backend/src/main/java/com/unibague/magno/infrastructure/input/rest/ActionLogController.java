package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.response.ActionLogResponse;
import com.unibague.magno.application.handler.impl.ActionLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/action-logs")
public class ActionLogController {

    private final ActionLogHandler actionLogHandler;

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ActionLogResponse>> getAllActionLogs() {
        List<ActionLogResponse> actionLogs = actionLogHandler.findAll();
        return ResponseEntity.ok(actionLogs);
    }

    @GetMapping(path = "/by-user-id", headers = "API-VERSION=1")
    public ResponseEntity<List<ActionLogResponse>> getActionLogsByUserId(@RequestParam Long userId) {
        List<ActionLogResponse> actionLogs = actionLogHandler.findByUserId(userId);
        return ResponseEntity.ok(actionLogs);
    }

    @GetMapping(path = "/by-date-range", headers = "API-VERSION=1")
    public ResponseEntity<List<ActionLogResponse>> getActionLogsByDateRange(@RequestParam String start, @RequestParam String end) {
        List<ActionLogResponse> actionLogs = actionLogHandler.findByTimestampBetween(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
        return ResponseEntity.ok(actionLogs);
    }

    @GetMapping(path = "/older-than", headers = "API-VERSION=1")
    public ResponseEntity<List<ActionLogResponse>> getActionLogsOlderThanDays(@RequestParam String date) {
        List<ActionLogResponse> actionLogs = actionLogHandler.getLogsOlderThanDays(
                LocalDateTime.parse(date)
        );
        return ResponseEntity.ok(actionLogs);
    }
}
