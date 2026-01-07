package com.portfolio.elite.customlogging.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {

    private final String headerName;
    private final boolean mdcEnabled;
    public static final String CORRELATION_ID_MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        var correlationId = request.getHeader(headerName);
        if (Objects.isNull(correlationId) || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        try {
            if (mdcEnabled) {
                MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
                response.setHeader(headerName, correlationId);
                log.debug("Correlation ID [{}] assigned to request [{}]",
                    correlationId, request.getRequestURI());
            }
            filterChain.doFilter(request, response);
        } finally {
            if (mdcEnabled) {
                MDC.remove(CORRELATION_ID_MDC_KEY);
            }
        }
    }
}
