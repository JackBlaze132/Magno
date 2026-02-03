package com.unibague.magno.infrastructure.input.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for health checks
 */
@RestController
public class HealthRestController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
