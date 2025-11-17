# Week 2 - Day 3 Summary

- Using `io.micrometer:micrometer-tracing-bridge-brave` dependency: traceId,spanId and correlationId can be included in logs via logback-spring.xml
- Created `CorrelationIdFilter` to include MDC key `correlationId`  by read the request header `X-Correlation-ID`.
- We may need to include this filter across different microservices available for which a starter module `elite-observability-starter` is created.
- `elite-observability-starter` lets you enable traceId,spanId and correlationId on the services logs when added as dependency.
- The module consists of `CorrelationIdFilter` which includes the MDC keys, auto-configuration is enabled by default by defining `org.springframework.boot.autoconfigure.AutoConfiguration.imports` under `resources/META-INF/spring` directory with `com.portfolio.elite.customlogging.config.EliteLoggingAutoConfiguration` configuration class.
- To enable `correlationId` MDC key, `elite-observability-starter` dependency is added on microservice and `application.yaml` includes below configuration
```yaml
elite:
  logging:
    config:
      correlation:
        enabled: true
        header-name: X-Correlation-ID
        mdc-enabled: true
```



