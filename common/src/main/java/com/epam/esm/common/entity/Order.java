package com.epam.esm.common.entity;

import com.epam.esm.common.audit.AuditablePersist;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "\"order\"")
public class Order implements AuditablePersist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "certificate_id")
    private Long certificateId;
    private BigDecimal price;
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    public Long getOrderId() {
        return orderId;
    }

    public Order setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Order setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public Order setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Order setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId)
                && Objects.equals(userId, order.userId)
                && Objects.equals(certificateId, order.certificateId)
                && Objects.equals(price, order.price)
                && Objects.equals(createDate, order.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, certificateId, price, createDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", certificateId=" + certificateId +
                ", price=" + price +
                ", createDate=" + createDate +
                '}';
    }
}
