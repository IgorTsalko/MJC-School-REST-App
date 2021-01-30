package com.epam.esm.server.entity;

import javax.validation.constraints.Positive;
import java.util.Objects;

public class OrderRequest {

    @Positive
    private Long certificateId;

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(certificateId, that.certificateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateId);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "certificateId=" + certificateId +
                '}';
    }
}
