package com.epam.esm.server.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class OrderRequest {

    @NotNull
    @Positive
    private Long giftCertificateId;

    public Long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(Long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(giftCertificateId, that.giftCertificateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificateId);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "giftCertificateId=" + giftCertificateId +
                '}';
    }
}
