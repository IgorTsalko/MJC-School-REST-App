package com.epam.esm.common.audit;

import java.time.LocalDateTime;

public interface AuditablePersist {

    void setCreateDate(LocalDateTime createDate);
}
