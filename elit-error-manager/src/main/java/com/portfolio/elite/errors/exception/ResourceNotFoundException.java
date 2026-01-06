package com.portfolio.elite.errors.exception;

import com.portfolio.elite.errors.constants.ErrorManagerConstants;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(
            String.format("%s not found for identifier %s", resourceName, identifier),
            ErrorManagerConstants.RESOURCE_NOT_FOUND,
            HttpStatus.NOT_FOUND
        );
    }

    public ResourceNotFoundException(String message) {
        super(message, ErrorManagerConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
