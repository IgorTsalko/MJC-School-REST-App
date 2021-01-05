package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class UpdateException extends GiftCertificateException {

    public UpdateException(int id) {
        super(ErrorDefinition.BAD_REQUEST, id);
    }
}
