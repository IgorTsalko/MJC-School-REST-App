package com.epam.esm.common.exception;

public class WrongCredentialException extends GiftCertificateException {

    public WrongCredentialException() {
        super(ErrorDefinition.WRONG_CREDENTIAL);
    }

    public WrongCredentialException(ErrorDefinition errorDefinition) {
        super(errorDefinition);
    }
}
