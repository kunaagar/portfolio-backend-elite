package com.portfolio.elite.errors.handlers;

import com.portfolio.elite.errors.api.ApiErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Catches all unhandled exceptions as a safety net.
 * Should be the LAST resort - if an exception reaches here, it's unexpected.
 */
@RestControllerAdvice
@Slf4j
@Order(3)
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDTO> handleException(Exception ex,
        HttpServletRequest request) {

        final var correlationId = extractCorrelationId(request);
        log.error("[CorrelationId: {}] Unexpected error occurred processing request [{}] - Exception type {}",
            correlationId, request.getRequestURI(), ex.getClass().getName(), ex);

        final var body = ApiErrorResponseDTO.builder()
            .correlationId(correlationId)
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("An unexpected error occurred processing request. Please contact support with correlation id: " + correlationId)
            .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
