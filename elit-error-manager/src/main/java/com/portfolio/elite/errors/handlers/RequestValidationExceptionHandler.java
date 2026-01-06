package com.portfolio.elite.errors.handlers;

import com.portfolio.elite.errors.api.ApiErrorResponseDTO;
import com.portfolio.elite.errors.constants.ErrorManagerConstants;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(2)
public class RequestValidationExceptionHandler extends BaseExceptionHandler {

    /**
     * Handles Bean Validation failures on @RequestBody
     * Triggered by @Valid annotation
     *
     * Example:
     * POST /api/v1/customers
     * { "email": "invalid", "age": -5 }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        final var correlationId = extractCorrelationId(request);

        log.warn("[CorrelationId: {}] Validation failed for request [{}]: {} field error(s)",
            correlationId, request.getRequestURI(), ex.getBindingResult().getErrorCount());

        final var errors = ex.getBindingResult().getFieldErrors().stream().map(this::mapFieldError).toList();

        final var body = ApiErrorResponseDTO.builder()
            .fieldErrors(errors)
            .errorCode(ErrorManagerConstants.VALIDATION_ERROR)
            .message("Validation failed")
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .correlationId(correlationId)
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException ex,
        HttpServletRequest request
    ) {

        final var correlationId = extractCorrelationId(request);

        log.warn("[CorrelationId: {}] Malformed JSON for request [{}]: {}",
            correlationId, request.getRequestURI(), ex.getMessage());

        final var body = ApiErrorResponseDTO.builder()
            .correlationId(correlationId)
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .message("Request body contains invalid JSON")
            .status(HttpStatus.BAD_REQUEST.value())
            .errorCode("MALFORMED_JSON")
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
