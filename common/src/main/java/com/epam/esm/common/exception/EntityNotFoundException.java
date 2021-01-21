package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class EntityNotFoundException extends GiftCertificateException {

    public EntityNotFoundException(ErrorDefinition errorDefinition, Long id) {
        super(errorDefinition, id);
    }
}
