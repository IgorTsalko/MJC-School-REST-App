package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class GiftCertificateException extends RuntimeException {

    private ErrorDefinition errorDefinition;
    private Long entityId;

    public GiftCertificateException(ErrorDefinition errorDefinition, Long id) {
        this.errorDefinition = errorDefinition;
        this.entityId = id;
    }

    public GiftCertificateException(ErrorDefinition errorDefinition, Long id, Throwable cause) {
        super(cause);
        this.errorDefinition = errorDefinition;
        this.entityId = id;
    }

    public ErrorDefinition getErrorDefinition() {
        return errorDefinition;
    }

    public Long getEntityId() {
        return entityId;
    }
}
