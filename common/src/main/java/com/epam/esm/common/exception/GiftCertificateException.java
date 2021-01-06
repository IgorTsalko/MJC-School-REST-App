package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class GiftCertificateException extends RuntimeException {

    private final ErrorDefinition errorDefinition;
    private final Object[] params;

    public GiftCertificateException(ErrorDefinition errorDefinition, Object[] params) {
        this.errorDefinition = errorDefinition;
        this.params = params;
    }

    public ErrorDefinition getErrorDefinition() {
        return errorDefinition;
    }

    public Object[] getParams() {
        return params;
    }
}
