package com.epam.esm.common;

import java.time.LocalDateTime;

public interface AuditableUpdate extends AuditablePersist {

    void setLastUpdateDate(LocalDateTime lastUpdateDate);
}
