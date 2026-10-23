package com.portfolio.elite.errors.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class EliteFeignClientException extends BusinessException {

    private final String serviceName;
    private final String downstreamErrorCode;
    private final String downstreamMessage;
    private final String downstreamCorrelationId;

    public EliteFeignClientException(
        String serviceName,
        HttpStatus httpStatus,
        String downstreamErrorCode,
        String downstreamMessage,
        String downstreamCorrelationId) {

        super(
            buildMessage(serviceName, downstreamMessage),
            downstreamErrorCode != null ? downstreamErrorCode : "DOWNSTREAM_SERVICE_ERROR",
            httpStatus
        );

        this.serviceName = serviceName;
        this.downstreamErrorCode = downstreamErrorCode;
        this.downstreamMessage = downstreamMessage;
        this.downstreamCorrelationId = downstreamCorrelationId;
    }

    /**
     * Factory method for unparseable downstream errors
     */
    public static EliteFeignClientException unparseable(String serviceName, HttpStatus httpStatus, String rawBody) {
        return new EliteFeignClientException(
            serviceName,
            httpStatus,
            "DOWNSTREAM_SERVICE_ERROR",
            "Downstream service returned error: " + truncate(rawBody, 200),
            null
        );
    }

    private static String buildMessage(String serviceName, String downstreamMessage) {
        return String.format("Call to %s failed: %s", serviceName, downstreamMessage);
    }

    private static String truncate(String value, int maxLength) {
        if (value == null) return "null";
        return value.length() > maxLength ? value.substring(0, maxLength) + "..." : value;
    }
}
