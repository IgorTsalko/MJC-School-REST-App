package com.epam.esm.server.entity;

public class ExceptionResponse {

    private int errorCode;
    private String[] details;

    public int getErrorCode() {
        return errorCode;
    }

    public ExceptionResponse setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String[] getDetails() {
        return details;
    }

    public ExceptionResponse setDetails(String... details) {
        this.details = details;
        return this;
    }
}
