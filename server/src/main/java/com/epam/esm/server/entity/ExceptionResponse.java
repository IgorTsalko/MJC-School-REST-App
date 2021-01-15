package com.epam.esm.server.entity;

import java.util.List;

public class ExceptionResponse {

    private int errorCode;
    private List<String> details;

    public int getErrorCode() {
        return errorCode;
    }

    public ExceptionResponse setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public List<String> getDetails() {
        return details;
    }

    public ExceptionResponse setDetails(List<String> details) {
        this.details = details;
        return this;
    }
}
