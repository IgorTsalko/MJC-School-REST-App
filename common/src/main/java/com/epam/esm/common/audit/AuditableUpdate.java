package com.epam.esm.common.audit;

import java.time.LocalDateTime;

public interface AuditableUpdate extends AuditablePersist {

    void setLastUpdateDate(LocalDateTime lastUpdateDate);
}
