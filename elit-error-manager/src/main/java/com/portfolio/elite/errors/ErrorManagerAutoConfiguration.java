package com.portfolio.elite.errors;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.portfolio.elite.errors.*")
@Slf4j
public class ErrorManagerAutoConfiguration {


    @PostConstruct
    protected void log() {
        log.debug("Exception handlers have been initialized");
    }
}
