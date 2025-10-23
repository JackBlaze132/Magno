package com.unibague.magno.infrastructure.util;

import com.unibague.magno.domain.model.CronJobExecutionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class CronJobLogContextService {

    public CronJobExecutionLog createCronJobExecutionLog(String jobName) {
        CronJobExecutionLog executionLog = new CronJobExecutionLog();
        executionLog.setJobName(jobName);
        executionLog.setStartTime(LocalDateTime.now());
        executionLog.setTimestamp(LocalDateTime.now());
        return executionLog;
    }

    public void finalizeExecutionLog(CronJobExecutionLog executionLog, String status, 
                                   String details, Integer recordsProcessed, Exception exception) {
        executionLog.setEndTime(LocalDateTime.now());
        executionLog.setStatus(status);
        executionLog.setDetails(details);
        executionLog.setRecordsProcessed(recordsProcessed);
        
        if (executionLog.getStartTime() != null && executionLog.getEndTime() != null) {
            long durationMs = ChronoUnit.MILLIS.between(executionLog.getStartTime(), executionLog.getEndTime());
            executionLog.setDurationMs(durationMs);
        }
        
        if (exception != null) {
            executionLog.setErrorMessage(exception.getMessage());
            executionLog.setStackTrace(getStackTraceAsString(exception));
        }
    }

    private String getStackTraceAsString(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }
}
