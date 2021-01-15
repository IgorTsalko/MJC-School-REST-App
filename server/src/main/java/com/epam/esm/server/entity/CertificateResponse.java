package com.epam.esm.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CertificateResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    private List<TagResponse> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public CertificateResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertificateResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificateResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CertificateResponse setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public CertificateResponse setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public CertificateResponse setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public CertificateResponse setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public CertificateResponse setTags(List<TagResponse> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateResponse that = (CertificateResponse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(price, that.price)
                && Objects.equals(duration, that.duration)
                && Objects.equals(tags, that.tags)
                && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, tags, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tag=" + tags +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
