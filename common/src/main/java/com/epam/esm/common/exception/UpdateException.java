package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class UpdateException extends GiftCertificateException {

    public UpdateException(Object... params) {
        super(ErrorDefinition.BAD_REQUEST, params);
    }
}
