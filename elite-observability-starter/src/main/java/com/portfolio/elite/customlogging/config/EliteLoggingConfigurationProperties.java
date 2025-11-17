package com.portfolio.elite.customlogging.config;

import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "elite.logging.config")
public class EliteLoggingConfigurationProperties {

    @Getter(AccessLevel.NONE)
    private final CorrelationConfig correlation;

    @RequiredArgsConstructor
    @Getter
    @ToString
    static class CorrelationConfig {

        private final boolean enabled;
        private final String headerName;
        private final boolean mdcEnabled;
    }

    public String getHeaderName() {
        return Optional.ofNullable(correlation).map(CorrelationConfig::getHeaderName).orElse(null);
    }

    public boolean isMDCEnabled() {
        return Optional.ofNullable(correlation).map(CorrelationConfig::isMdcEnabled).orElse(false);
    }

    @PostConstruct
    protected void log() {
        log.info("Logging :: Correlation configuration : {}", correlation);
    }
}
