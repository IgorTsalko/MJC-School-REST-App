package com.epam.esm.common.exception;

public class BadCredentialsException extends GiftCertificateException {

    public BadCredentialsException() {
        super(ErrorDefinition.WRONG_CREDENTIAL);
    }

    public BadCredentialsException(ErrorDefinition errorDefinition) {
        super(errorDefinition);
    }
}
