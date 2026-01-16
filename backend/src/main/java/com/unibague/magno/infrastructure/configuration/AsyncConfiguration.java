package com.unibague.magno.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration class to enable asynchronous method execution.
 * Allows the use of @Async annotation on methods for non-blocking operations.
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
}

