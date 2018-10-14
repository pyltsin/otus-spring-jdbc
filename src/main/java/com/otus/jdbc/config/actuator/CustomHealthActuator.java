package com.otus.jdbc.config.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class CustomHealthActuator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        final LocalDateTime now = LocalDateTime.now();
        final DayOfWeek dayOfWeek = now.getDayOfWeek();
        if (dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            builder.down().withDetail("day", dayOfWeek.getValue());
        } else {
            builder.up().withDetail("day", dayOfWeek.getValue());
        }
    }
}
