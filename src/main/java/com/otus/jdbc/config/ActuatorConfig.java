package com.otus.jdbc.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {
    @Bean
    public Counter enterCounter(MeterRegistry registry) {
        return registry.counter("enter");
    }
}
