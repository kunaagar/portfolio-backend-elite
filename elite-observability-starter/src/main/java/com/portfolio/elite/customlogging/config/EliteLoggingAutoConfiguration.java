package com.portfolio.elite.customlogging.config;

import com.portfolio.elite.customlogging.filters.CorrelationIdFilter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Slf4j
@AutoConfiguration
@ConditionalOnClass(MDC.class)
@EnableConfigurationProperties(EliteLoggingConfigurationProperties.class)
@RequiredArgsConstructor
public class EliteLoggingAutoConfiguration {

    private final EliteLoggingConfigurationProperties config;

    @Bean
    @ConditionalOnMissingBean(name = "correlationFilter")
    @ConditionalOnProperty(
        name = "elite.logging.config.correlation.enabled",
        havingValue = "true",
        matchIfMissing = true
    )
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Filter correlationFilter() {
        log.info("Initializing correlationId filter..");
        return new CorrelationIdFilter(config.getHeaderName(), config.isMDCEnabled());
    }

    @PostConstruct
    public void init() {
        log.info("EliteLoggingAutoConfiguration loaded with correlation header :{}", config.getHeaderName());
    }

}
