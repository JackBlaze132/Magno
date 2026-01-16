package com.unibague.magno.infrastructure.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.infrastructure.configuration.security.CustomOidcUserWithUserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service for creating action log entries from HTTP requests.
 * Builds {@link ActionLog} objects with request/response details, user information,
 * and execution time. Automatically filters sensitive data from request/response bodies.
 */
@Service
@RequiredArgsConstructor
public class ActionLogContextService {

    private final IUserServicePort userServicePort;
    private final ObjectMapper objectMapper;

    private static final int MAX_BODY_SIZE = 10240; // 10KB
    private static final List<String> SENSITIVE_FIELDS = Arrays.asList(
            "password", "passwd", "pwd", "token", "authorization", "auth",
            "secret", "apikey", "api_key", "api-key", "creditcard", "credit_card"
    );

    public ActionLog createActionLog(HttpServletRequest request, int responseStatus,
                                     String responseBody, long executionTimeMs) {
        ActionLog actionLog = new ActionLog();

        // Basic request information
        actionLog.setHttpMethod(request.getMethod());
        actionLog.setRequestUrl(request.getRequestURL().toString());
        actionLog.setResponseStatus(responseStatus);
        actionLog.setTimestamp(LocalDateTime.now());
        actionLog.setExecutionTimeMs(executionTimeMs);

        // Response body (filtered and limited)
        actionLog.setResponseBody(filterAndLimitBody(responseBody));

        // HTTP context information
        actionLog.setClientIp(getClientIpAddress(request));
        actionLog.setUserAgent(request.getHeader("User-Agent"));
        actionLog.setSessionId(request.getSession(false) != null ?
                request.getSession(false).getId() : null);

        // User information from authentication
        extractUserInfo(actionLog);

        return actionLog;
    }

    public void setRequestBody(ActionLog actionLog, String requestBody) {
        actionLog.setRequestBody(filterAndLimitBody(requestBody));
    }

    private String filterAndLimitBody(String body) {
        if (body == null || body.isEmpty()) {
            return null;
        }

        try {
            // Try to parse as JSON and filter sensitive fields
            JsonNode jsonNode = objectMapper.readTree(body);
            filterSensitiveFields(jsonNode);
            String filtered = objectMapper.writeValueAsString(jsonNode);

            // Limit size
            if (filtered.length() > MAX_BODY_SIZE) {
                return filtered.substring(0, MAX_BODY_SIZE) + "... [TRUNCATED]";
            }
            return filtered;
        } catch (Exception e) {
            // Not JSON or parsing error, just limit size and redact patterns
            String redacted = redactSensitivePatterns(body);
            if (redacted.length() > MAX_BODY_SIZE) {
                return redacted.substring(0, MAX_BODY_SIZE) + "... [TRUNCATED]";
            }
            return redacted;
        }
    }

    private void filterSensitiveFields(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<String> fieldNames = objectNode.fieldNames();
            
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (isSensitiveField(fieldName)) {
                    objectNode.put(fieldName, "***FILTERED***");
                } else {
                    JsonNode childNode = objectNode.get(fieldName);
                    if (childNode != null) {
                        filterSensitiveFields(childNode);
                    }
                }
            }
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                filterSensitiveFields(item);
            }
        }
    }

    private boolean isSensitiveField(String fieldName) {
        String lowerFieldName = fieldName.toLowerCase();
        return SENSITIVE_FIELDS.stream()
                .anyMatch(lowerFieldName::contains);
    }

    private String redactSensitivePatterns(String text) {
        // Redact credit card patterns
        text = text.replaceAll("\\b\\d{4}[\\s-]?\\d{4}[\\s-]?\\d{4}[\\s-]?\\d{4}\\b", "****-****-****-****");
        
        // Redact email in certain contexts (like in tokens)
        text = text.replaceAll("\"email\"\\s*:\\s*\"[^\"]+\"", "\"email\":\"***FILTERED***\"");
        
        // Redact common sensitive field patterns
        for (String sensitive : SENSITIVE_FIELDS) {
            Pattern pattern = Pattern.compile(
                    "\"" + sensitive + "\"\\s*:\\s*\"[^\"]+\"",
                    Pattern.CASE_INSENSITIVE
            );
            text = pattern.matcher(text).replaceAll("\"" + sensitive + "\":\"***FILTERED***\"");
        }
        
        return text;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    private void extractUserInfo(ActionLog actionLog) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof CustomOidcUserWithUserId oidcUser) {
                    actionLog.setUserEmail(oidcUser.getAttribute("email"));
                } else if (principal instanceof GoogleIdToken.Payload payload) {
                    actionLog.setUserEmail(payload.getEmail());
                }
            }
        } catch (Exception e) {
            // If we can't extract user info, just continue without it
        }

        if (actionLog.getUserEmail() != null) {
            try {
                Long userId = userServicePort.findByEmail(actionLog.getUserEmail()).getId();
                actionLog.setUserId(userId);
            } catch (Exception e) {
                // User might not exist in database yet, that's okay
            }
        }
    }
}

