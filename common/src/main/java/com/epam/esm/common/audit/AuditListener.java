package com.epam.esm.common.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    private void preCreate(Object object) {
        LocalDateTime now = LocalDateTime.now();
        if (object instanceof AuditablePersist) {
            AuditablePersist auditablePersist = (AuditablePersist) object;
            auditablePersist.setCreateDate(now);
        }
        if (object instanceof AuditableUpdate) {
            AuditableUpdate auditableUpdate = (AuditableUpdate) object;
            auditableUpdate.setCreateDate(now);
            auditableUpdate.setLastUpdateDate(now);
        }
    }

    @PreUpdate
    private void preUpdate(Object object) {
        if (object instanceof AuditableUpdate) {
            AuditableUpdate auditableUpdate = (AuditableUpdate) object;
            auditableUpdate.setLastUpdateDate(LocalDateTime.now());
        }
    }
}
