package com.epam.esm.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorDefinition {

    TOKEN_INVALID(40001, "token.invalid", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(40101, "token.expired", HttpStatus.UNAUTHORIZED),
    CERTIFICATE_NOT_FOUND(40401, "certificate.not-found", HttpStatus.NOT_FOUND),
    TAG_NOT_FOUND(40402, "tag.not-found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(40403, "user.not-found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(40404, "order.not-found", HttpStatus.NOT_FOUND),
    WRONG_CREDENTIAL(40405, "wrong-credential", HttpStatus.NOT_FOUND);

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
