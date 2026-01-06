package com.portfolio.elite.errors.handlers;

import com.portfolio.elite.errors.api.ApiErrorResponseDTO;
import com.portfolio.elite.errors.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Slf4j
public abstract class BaseExceptionHandler {

    protected static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    protected final void logException(BusinessException ex, HttpServletRequest request, HttpStatus status, final String correlationId) {
        // Log 4xx as WARN, 5xx as ERROR
        if (status.is4xxClientError()) {
            log.warn("[CorrelationId: {}] Business exception for request [{}]: {}",
                correlationId, request.getRequestURI(), ex.getMessage());
        } else {
            log.error("[CorrelationId: {}] Business exception for request [{}]",
                correlationId, request.getRequestURI(), ex);
        }
    }

    protected final String extractCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null) {
            correlationId = MDC.get("correlationId"); // From your elite-observability-starter
        }
        return correlationId != null ? correlationId : "N/A";
    }

    protected final ApiErrorResponseDTO.FieldError mapFieldError(FieldError fieldError) {

        return ApiErrorResponseDTO.FieldError.builder()
            .field(fieldError.getField())
            .message(fieldError.getDefaultMessage())
            .rejectedValue(fieldError.getRejectedValue())
            .build();
    }

}
