package com.shop.coreutils.configuration;

import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.RetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
public class Resilience4jConfig {
    Logger logger = LoggerFactory.getLogger("Resilience4jConfig");
    @Value("${retry.waitDuration: 2}")
    long retryWaitDuration;

    @Value("${retry.maxAttempts: 5}")
    int maxAttempts;

    @Value("${spring.application.name}")
    String instanceName;
    @Bean
    public RetryConfig retryConfig() {
        logger.info("zzz Starting Retry Config Customizer");

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(maxAttempts)
             //   .waitDuration(Duration.of(retryWaitDuration, SECONDS))
                .intervalFunction(IntervalFunction.ofExponentialBackoff(retryWaitDuration * 1000, 2))
                .build();
        return config;
    }
}
