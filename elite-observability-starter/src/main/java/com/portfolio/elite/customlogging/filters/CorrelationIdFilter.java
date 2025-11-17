package com.portfolio.elite.customlogging.filters;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@RequiredArgsConstructor
@Slf4j
public class CorrelationIdFilter implements Filter {

    private final String headerName;
    private final boolean mdcEnabled;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        var correlationId = ((HttpServletRequest) servletRequest).getHeader(headerName);
        if (Objects.isNull(correlationId) || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        try {
            if (mdcEnabled) {
                MDC.put("correlationId", correlationId);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (mdcEnabled) {
                MDC.remove("correlationId");
            }
        }
    }
}
