package com.unibague.magno.infrastructure.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.infrastructure.configuration.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ErrorLogContextService {

    private final SecurityService securityService;

    public ErrorLog createErrorLog(Exception exception, String errorCode, String errorMessage, 
                                 HttpServletRequest request) {
        ErrorLog errorLog = new ErrorLog();
        
        // Basic error information
        errorLog.setErrorCode(errorCode);
        errorLog.setErrorMessage(errorMessage);
        errorLog.setDetails(exception.getMessage());
        errorLog.setExceptionClassName(exception.getClass().getName());
        errorLog.setStackTrace(getStackTraceAsString(exception));
        errorLog.setTimestamp(LocalDateTime.now());
        
        // HTTP request information
        if (request != null) {
            errorLog.setHttpMethod(request.getMethod());
            errorLog.setRequestUrl(request.getRequestURL().toString());
            errorLog.setRequestParams(extractRequestParams(request));
            errorLog.setClientIp(getClientIpAddress(request));
            errorLog.setUserAgent(request.getHeader("User-Agent"));
            errorLog.setSessionId(request.getSession(false) != null ? 
                request.getSession(false).getId() : null);
        }
        
        // User information from authentication
        extractUserInfo(errorLog);
        
        return errorLog;
    }

    private String getStackTraceAsString(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }

    private String extractRequestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        
        // Query parameters
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            
            // Filter sensitive parameters
            if (!isSensitiveParameter(key)) {
                params.put(key, String.join(",", values));
            }
        }
        
        // Headers (excluding sensitive ones)
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!isSensitiveHeader(headerName)) {
                params.put("header_" + headerName, request.getHeader(headerName));
            }
        }
        
        return params.isEmpty() ? null : params.toString();
    }

    private boolean isSensitiveParameter(String paramName) {
        String lowerParamName = paramName.toLowerCase();
        return lowerParamName.contains("password") || 
               lowerParamName.contains("token") || 
               lowerParamName.contains("secret") ||
               lowerParamName.contains("key");
    }

    private boolean isSensitiveHeader(String headerName) {
        String lowerHeaderName = headerName.toLowerCase();
        return lowerHeaderName.equals("authorization") || 
               lowerHeaderName.equals("cookie") ||
               lowerHeaderName.contains("token") ||
               lowerHeaderName.contains("secret");
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

    private void extractUserInfo(ErrorLog errorLog) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                
                if (principal instanceof DefaultOidcUser oidcUser) {
                    errorLog.setUserEmail(oidcUser.getAttribute("email"));
                    // Try to get user ID from the email if possible
                    // This would require a call to userService, but we'll keep it simple for now
                } else if (principal instanceof GoogleIdToken.Payload payload) {
                    errorLog.setUserEmail(payload.getEmail());
                    // Try to get user ID from the email if possible
                }
            }
        } catch (Exception e) {
            // If we can't extract user info, just continue without it
            // This prevents the error logging itself from failing
        }
    }
}
