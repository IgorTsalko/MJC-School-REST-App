package com.epam.esm.common.exception;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.TagDTO;

public class GiftCertificateException extends RuntimeException {

    private final ErrorDefinition errorDefinition;
    private final String entityName;
    private final int entityId;

    public GiftCertificateException(ErrorDefinition errorDefinition, Class<?> clazz, int id) {
        this.errorDefinition = errorDefinition;
        this.entityId = id;
        if (clazz.isAssignableFrom(CertificateDTO.class)) {
            entityName = "Certificate";
        } else if (clazz.isAssignableFrom(TagDTO.class)) {
            entityName = "Tag";
        } else {
            entityName = "unknown";
        }
    }

    public ErrorDefinition getErrorDefinition() {
        return errorDefinition;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getEntityId() {
        return entityId;
    }
}
