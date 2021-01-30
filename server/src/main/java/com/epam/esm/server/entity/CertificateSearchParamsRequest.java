package com.epam.esm.server.entity;

import com.epam.esm.common.entity.SortOrder;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CertificateSearchParamsRequest {

    @Size(min = 2, max = 50)
    private String name;
    @Size(min = 2, max = 250)
    private String description;
    @Size(min = 2)
    private String tags;
    @Size(min = 2, max = 20)
    private String sort;
    private SortOrder sortOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateSearchParamsRequest that = (CertificateSearchParamsRequest) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(tags, that.tags)
                && Objects.equals(sort, that.sort)
                && sortOrder == that.sortOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, tags, sort, sortOrder);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", sort='" + sort + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
