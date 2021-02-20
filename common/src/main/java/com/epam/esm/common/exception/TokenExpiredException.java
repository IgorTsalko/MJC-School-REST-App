package com.epam.esm.common.exception;

public class TokenExpiredException extends GiftCertificateException {

    public TokenExpiredException() {
        super(ErrorDefinition.TOKEN_EXPIRED);
    }
}
