package com.epam.esm.common;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    private void preCreate(Object object) {
        if (AuditablePersist.class.isAssignableFrom(object.getClass())) {
            AuditablePersist auditablePersist = (AuditablePersist) object;
            auditablePersist.setCreateDate(LocalDateTime.now());
        }
        if (AuditableUpdate.class.isAssignableFrom(object.getClass())) {
            AuditableUpdate auditableUpdate = (AuditableUpdate) object;
            auditableUpdate.setLastUpdateDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(Object object) {
        if (AuditableUpdate.class.isAssignableFrom(object.getClass())) {
            AuditableUpdate auditableUpdate = (AuditableUpdate) object;
            auditableUpdate.setLastUpdateDate(LocalDateTime.now());
        }
    }
}
