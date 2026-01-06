package com.portfolio.elite.errors.api;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value // no setter to ensure immutability.
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponseDTO {

    /**
     * ISO-8601 timestamp when error occurred
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime timestamp;

    /**
     * HTTP status code (e.g., 404, 400, 500)
     */
    int status;

    /**
     * Machine-readable error code for client-side handling
     * Examples: "ERR-4001", "ERR-5001"
     */
    String errorCode;

    /**
     * Request path that caused the error
     */
    String path;

    /**
     * Correlation ID for distributed tracing
     * Used to track request across microservices
     */
    String correlationId;

    /**
     * Human-readable error message (safe for clients)
     */
    String message;

    /**
     * Field-level validation errors (only for 400 validation failures)
     */
    List<FieldError>  fieldErrors;

    /**
     * Represents a single field validation error
     */
    @Value
    @Builder
    public static class FieldError {
        String field;      // "email"
        String message;    // "must be a valid email address"
        Object rejectedValue; // "invalid-email"
    }

}
