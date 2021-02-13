package com.epam.esm.common.exception;

public class GiftCertificateException extends RuntimeException {

    private ErrorDefinition errorDefinition;
    private Object data;

    public GiftCertificateException(ErrorDefinition errorDefinition) {
        this.errorDefinition = errorDefinition;
    }

    public GiftCertificateException(ErrorDefinition errorDefinition, Object data) {
        this.errorDefinition = errorDefinition;
        this.data = data;
    }

    public GiftCertificateException(ErrorDefinition errorDefinition, Object data, Throwable cause) {
        super(cause);
        this.errorDefinition = errorDefinition;
        this.data = data;
    }

    public ErrorDefinition getErrorDefinition() {
        return errorDefinition;
    }

    public Object getData() {
        return data;
    }
}
