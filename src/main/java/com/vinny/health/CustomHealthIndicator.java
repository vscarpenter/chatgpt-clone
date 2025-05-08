package com.vinny.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Custom logic to determine health status
        boolean isHealthy = checkCustomHealth();
        if (isHealthy) {
            return Health.up().withDetail("CustomHealth", "Service is healthy").build();
        } else {
            return Health.down().withDetail("CustomHealth", "Service is unhealthy").build();
        }
    }

    private boolean checkCustomHealth() {
        // Add your custom health check logic here
        return true;
    }
}