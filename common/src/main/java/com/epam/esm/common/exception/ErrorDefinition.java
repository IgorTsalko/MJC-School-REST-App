package com.epam.esm.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorDefinition {

    TOKEN_INVALID(CustomErrorCodes.TOKEN_INVALID, "token.invalid", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(CustomErrorCodes.TOKEN_EXPIRED, "token.expired", HttpStatus.UNAUTHORIZED),
    GIFT_CERTIFICATE_NOT_FOUND(CustomErrorCodes.GIFT_CERTIFICATE_NOT_FOUND,
            "gift-certificate.not-found", HttpStatus.NOT_FOUND),
    TAG_NOT_FOUND(CustomErrorCodes.TAG_NOT_FOUND, "tag.not-found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(CustomErrorCodes.USER_NOT_FOUND, "user.not-found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(CustomErrorCodes.ORDER_NOT_FOUND, "order.not-found", HttpStatus.NOT_FOUND);

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
