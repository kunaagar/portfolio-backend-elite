package com.portfolio.elite.errors.exception;

import com.portfolio.elite.errors.constants.ErrorManagerConstants;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException() {
        super(ErrorManagerConstants.INTERNAL_SERVER_ERROR, "Unexpected error occurred while processing the request.",
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message) {
        super(ErrorManagerConstants.INTERNAL_SERVER_ERROR, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
