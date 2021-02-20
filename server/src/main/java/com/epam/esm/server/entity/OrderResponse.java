package com.epam.esm.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderResponse extends RepresentationModel<OrderResponse> {

    private Long orderId;
    private Long userId;
    private Long giftCertificateId;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    public Long getOrderId() {
        return orderId;
    }

    public OrderResponse setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getGiftCertificateId() {
        return giftCertificateId;
    }

    public OrderResponse setGiftCertificateId(Long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderResponse setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public OrderResponse setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponse that = (OrderResponse) o;
        return Objects.equals(orderId, that.orderId)
                && Objects.equals(userId, that.userId)
                && Objects.equals(giftCertificateId, that.giftCertificateId)
                && Objects.equals(price, that.price)
                && Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, giftCertificateId, price, createDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", giftCertificateId=" + giftCertificateId +
                ", price=" + price +
                ", createDate=" + createDate +
                '}';
    }
}
