package com.epam.esm.common;

import java.time.LocalDateTime;

public interface AuditablePersist {

    void setCreateDate(LocalDateTime createDate);
}
