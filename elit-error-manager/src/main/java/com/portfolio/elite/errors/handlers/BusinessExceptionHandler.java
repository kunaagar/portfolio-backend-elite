package com.portfolio.elite.errors.handlers;

import com.portfolio.elite.errors.api.ApiErrorResponseDTO;
import com.portfolio.elite.errors.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(1)
public class BusinessExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleBusinessException(
        BusinessException ex,
        HttpServletRequest request) {
        final var correlationId = extractCorrelationId(request);
        logException(ex, request, ex.getHttpStatus(), correlationId);

        final var body = ApiErrorResponseDTO.builder()
            .correlationId(correlationId)
            .timestamp(LocalDateTime.now())
            .errorCode(ex.getErrorCode())
            .status(ex.getHttpStatus().value())
            .path(request.getRequestURI())
            .message(ex.getMessage())
            .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }
}
