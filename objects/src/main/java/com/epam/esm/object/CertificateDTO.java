package com.epam.esm.object;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class CertificateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6531961550283877234L;

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    public Integer getId() {
        return id;
    }

    public CertificateDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertificateDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificateDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CertificateDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public CertificateDTO setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public CertificateDTO setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public CertificateDTO setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateDTO that = (CertificateDTO) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(price, that.price)
                && Objects.equals(duration, that.duration)
                && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
