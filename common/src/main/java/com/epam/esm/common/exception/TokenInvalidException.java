package com.epam.esm.common.exception;

public class TokenInvalidException extends GiftCertificateException {

    public TokenInvalidException() {
        super(ErrorDefinition.TOKEN_INVALID);
    }
}
