package com.epam.esm.common;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorDefinition {

    BAD_REQUEST(40001, "Bad Request"),
    NOT_FOUND(40401, "Requested resource not found for id %d");

    private final int errorCode;
    private final String errorMessageTemplate;

    ErrorDefinition(int errorCode, String errorMessageTemplate) {
        this.errorCode = errorCode;
        this.errorMessageTemplate = errorMessageTemplate;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessageTemplate;
    }
}
