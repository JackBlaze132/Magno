package com.unibague.magno.infrastructure.util;

import com.unibague.magno.domain.api.ICronJobExecutionLogServicePort;
import com.unibague.magno.domain.model.CronJobExecutionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronJobLogFileService {

    private final ICronJobExecutionLogServicePort cronJobExecutionLogServicePort;

    public void generateLogFile(String jobName, int days) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(days);
        
        List<CronJobExecutionLog> logs = cronJobExecutionLogServicePort.findByDateRange(startDate, endDate);
        
        if (jobName != null && !jobName.isEmpty()) {
            logs = logs.stream()
                    .filter(log -> log.getJobName().equals(jobName))
                    .toList();
        }
        
        String fileName = generateFileName(jobName, endDate);
        
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(generateLogContent(logs, jobName, startDate, endDate));
        } catch (IOException e) {
            throw new RuntimeException("Error generating log file: " + e.getMessage(), e);
        }
    }

    private String generateFileName(String jobName, LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.format(formatter);
        
        if (jobName != null && !jobName.isEmpty()) {
            return String.format("CRONJOB_EXECUTION_LOG_%s_%s.txt", jobName, dateStr);
        } else {
            return String.format("CRONJOB_EXECUTION_LOG_ALL_%s.txt", dateStr);
        }
    }

    private String generateLogContent(List<CronJobExecutionLog> logs, String jobName, 
                                   LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder content = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        content.append("CRONJOB EXECUTION LOG REPORT\n");
        content.append("============================\n\n");
        content.append("Generated: ").append(LocalDateTime.now().format(formatter)).append("\n");
        content.append("Period: ").append(startDate.format(formatter))
               .append(" - ").append(endDate.format(formatter)).append("\n");
        
        if (jobName != null && !jobName.isEmpty()) {
            content.append("Job Filter: ").append(jobName).append("\n");
        } else {
            content.append("Job Filter: ALL JOBS\n");
        }
        
        content.append("Total Executions: ").append(logs.size()).append("\n\n");
        
        for (CronJobExecutionLog log : logs) {
            content.append("Job: ").append(log.getJobName()).append("\n");
            content.append("Execution Time: ").append(log.getStartTime().format(formatter));
            
            if (log.getEndTime() != null) {
                content.append(" - ").append(log.getEndTime().format(formatter));
            }
            content.append("\n");
            
            content.append("Status: ").append(log.getStatus()).append("\n");
            
            if (log.getDurationMs() != null) {
                content.append("Duration: ").append(formatDuration(log.getDurationMs())).append("\n");
            }
            
            if (log.getRecordsProcessed() != null) {
                content.append("Records Processed: ").append(log.getRecordsProcessed()).append("\n");
            }
            
            if (log.getDetails() != null && !log.getDetails().isEmpty()) {
                content.append("Details: ").append(log.getDetails()).append("\n");
            }
            
            if (log.getErrorMessage() != null && !log.getErrorMessage().isEmpty()) {
                content.append("Error: ").append(log.getErrorMessage()).append("\n");
            }
            
            content.append("\n").append("-".repeat(50)).append("\n\n");
        }
        
        return content.toString();
    }

    private String formatDuration(Long durationMs) {
        if (durationMs == null) return "N/A";
        
        long seconds = durationMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            return String.format("%d hours, %d minutes, %d seconds", hours, minutes % 60, seconds % 60);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d seconds", minutes, seconds % 60);
        } else {
            return String.format("%d seconds", seconds);
        }
    }
}
