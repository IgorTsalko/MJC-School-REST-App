package com.epam.esm.server.entity;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class GiftCertificateCreateRequest {

    @NotNull
    @Size(min = 2, max = 50)
    private String title;
    @Size(min = 3, max = 250)
    private String description;
    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal price;
    @NotNull
    @Positive
    private Integer duration;
    @Valid
    private List<TagRequest> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<TagRequest> getTags() {
        return tags;
    }

    public void setTags(List<TagRequest> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateCreateRequest that = (GiftCertificateCreateRequest) o;
        return Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(price, that.price)
                && Objects.equals(duration, that.duration)
                && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, duration, tags);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
