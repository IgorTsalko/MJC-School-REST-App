package com.epam.esm.common;

import java.time.LocalDateTime;

public interface AuditableUpdate {

    void setLastUpdateDate(LocalDateTime lastUpdateDate);
}
