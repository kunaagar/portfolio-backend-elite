package com.portfolio.elite.errors.decoder.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.elite.errors.api.ApiErrorResponseDTO;
import com.portfolio.elite.errors.exception.EliteFeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        final var httpStatus = HttpStatus.resolve(response.status());
        if (httpStatus == null) {
            log.warn("Unknown HTTP status code: {}", response.status());
            return defaultErrorDecoder.decode(methodKey, response);
        }
        final String serviceName = extractServiceName(methodKey);

        try {
            if (response.body() != null) {
                String responseBody = new String(
                    response.body().asInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
                );

                log.debug("Downstream service '{}' error response body: {}", serviceName, responseBody);

                ApiErrorResponseDTO errorResponse = parseErrorResponse(responseBody);

                if (errorResponse != null) {
                    log.warn("Downstream service '{}' returned structured error: status={}, errorCode={}, message={}, correlationId={}",
                        serviceName,
                        errorResponse.getStatus(),
                        errorResponse.getErrorCode(),
                        errorResponse.getMessage(),
                        errorResponse.getCorrelationId());

                    return new EliteFeignClientException(
                        serviceName,
                        httpStatus,
                        errorResponse.getErrorCode(),
                        errorResponse.getMessage(),
                        errorResponse.getCorrelationId()
                    );
                } else {
                    // Response body exists but isn't our format
                    log.warn("Downstream service '{}' returned unparseable error: status={}, body={}",
                        serviceName, response.status(), responseBody);

                    return EliteFeignClientException.unparseable(serviceName, httpStatus, responseBody);
                }
            }
        } catch (IOException e) {
            log.warn("Failed to read error response body from '{}': {}", serviceName, e.getMessage());
        }

        // No response body
        log.warn("Downstream service '{}' returned error with no body: status={}", serviceName, response.status());
        return EliteFeignClientException.unparseable(serviceName, httpStatus, "No response body");
    }

    private ApiErrorResponseDTO parseErrorResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, ApiErrorResponseDTO.class);
        } catch (Exception e) {
            log.debug("Could not parse error response as ApiErrorResponseDTO: {}", e.getMessage());
            return null;
        }
    }

    private String extractServiceName(String methodKey) {
        int hashIndex = methodKey.indexOf('#');
        return hashIndex > 0 ? methodKey.substring(0, hashIndex) : methodKey;
    }


}
