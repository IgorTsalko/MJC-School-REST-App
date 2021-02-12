package com.epam.esm.common.exception;

public class EntityNotFoundException extends GiftCertificateException {

    public EntityNotFoundException(ErrorDefinition errorDefinition, Object data) {
        super(errorDefinition, data);
    }

    public EntityNotFoundException(ErrorDefinition errorDefinition, Object data, Throwable cause) {
        super(errorDefinition, data, cause);
    }
}
