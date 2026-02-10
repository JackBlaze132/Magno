package com.unibague.magno.infrastructure.filter;

import com.unibague.magno.domain.api.IActionLogServicePort;
import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.infrastructure.util.ActionLogContextService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Servlet filter for auditing user actions.
 * Logs successful non-GET requests (POST, PUT, PATCH, DELETE) including request/response
 * bodies and execution time. Runs late in the filter chain to capture complete responses.
 * Logging is performed asynchronously to avoid impacting request performance.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1) // Ensure this filter runs late in the chain
@RequiredArgsConstructor
public class ActionLoggingFilter implements Filter {

    private final IActionLogServicePort actionLogServicePort;
    private final ActionLogContextService actionLogContextService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Skip GET requests
        if ("GET".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // Wrap request and response to cache their content
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);

        long startTime = System.currentTimeMillis();

        try {
            // Continue with the request
            chain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            // Only log successful responses (2xx status codes)
            int status = wrappedResponse.getStatus();
            if (status >= 200 && status < 300) {
                try {
                    logAction(wrappedRequest, wrappedResponse, executionTime);
                } catch (Exception e) {
                    // Don't let logging errors affect the response
                    // Just silently continue
                }
            }

            // Copy the cached response content to the actual response
            wrappedResponse.copyBodyToResponse();
        }
    }

    @Async
    private void logAction(ContentCachingRequestWrapper request,
                          ContentCachingResponseWrapper response,
                          long executionTime) {
        try {
            // Extract request body
            String requestBody = getContentAsString(request.getContentAsByteArray(),
                    request.getCharacterEncoding());

            // Extract response body (skip binary content types)
            String responseBody = null;
            if (!isBinaryContentType(response.getContentType())) {
                responseBody = getContentAsString(response.getContentAsByteArray(),
                        response.getCharacterEncoding());
            } else {
                responseBody = "[BINARY CONTENT - " + response.getContentType() + "]";
            }

            // Create action log
            ActionLog actionLog = actionLogContextService.createActionLog(
                    request,
                    response.getStatus(),
                    responseBody,
                    executionTime
            );

            // Set request body separately (after filtering)
            actionLogContextService.setRequestBody(actionLog, requestBody);

            // Save asynchronously
            actionLogServicePort.save(actionLog);
        } catch (Exception e) {
            // Silently fail - logging should never break the application
        }
    }

    private boolean isBinaryContentType(String contentType) {
        if (contentType == null) {
            return false;
        }
        String lowerContentType = contentType.toLowerCase();
        return lowerContentType.contains("application/pdf") ||
               lowerContentType.contains("application/octet-stream") ||
               lowerContentType.contains("image/") ||
               lowerContentType.contains("audio/") ||
               lowerContentType.contains("video/") ||
               lowerContentType.contains("application/zip") ||
               lowerContentType.contains("application/x-") ||
               lowerContentType.contains("application/vnd.");
    }

    private String getContentAsString(byte[] content, String encoding) {
        if (content == null || content.length == 0) {
            return null;
        }
        try {
            return new String(content, encoding != null ? encoding : StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return null;
        }
    }
}

