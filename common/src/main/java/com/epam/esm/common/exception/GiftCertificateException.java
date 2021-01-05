package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class GiftCertificateException extends RuntimeException {

    private ErrorDefinition errorDefinition;
    private Object[] params;

    public GiftCertificateException(Object... params) {
        this.params = params;
    }

    public ErrorDefinition getErrorDefinition() {
        return errorDefinition;
    }

    public Object[] getParams() {
        return params;
    }
}
