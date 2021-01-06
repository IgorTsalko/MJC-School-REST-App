package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class EntityNotFoundException extends GiftCertificateException {

    public EntityNotFoundException(Object... params) {
        super(ErrorDefinition.NOT_FOUND, params);
    }
}
