package com.epam.esm.common.exception;

import com.epam.esm.common.ErrorDefinition;

public class IncorrectBodyException extends GiftCertificateException {

    public IncorrectBodyException() {
        super(ErrorDefinition.BAD_REQUEST);
    }
}
