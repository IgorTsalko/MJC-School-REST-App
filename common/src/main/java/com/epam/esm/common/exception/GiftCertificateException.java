package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class GiftCertificateException extends RuntimeException {

    private final ErrorDefinition errorDefinition;
    private final int entityId;

    public GiftCertificateException(ErrorDefinition errorDefinition, int id) {
        this.errorDefinition = errorDefinition;
        this.entityId = id;
    }

    public ErrorDefinition getErrorDefinition() {
        return errorDefinition;
    }

    public int getEntityId() {
        return entityId;
    }
}
