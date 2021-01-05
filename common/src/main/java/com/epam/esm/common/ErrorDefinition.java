package com.epam.esm.common;

import org.springframework.http.HttpStatus;

public enum ErrorDefinition {

    BAD_REQUEST(40001, "Bad Request for id %s", HttpStatus.BAD_REQUEST),
    NOT_FOUND(40401, "Requested resource not found for id %s", HttpStatus.NOT_FOUND);

    private final int errorCode;
    private final String errorMessageTemplate;
    private final HttpStatus httpStatus;

    ErrorDefinition(int errorCode, String errorMessageTemplate, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessageTemplate = errorMessageTemplate;
        this.httpStatus = httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessageTemplate() {
        return errorMessageTemplate;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
