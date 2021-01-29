package com.epam.esm.common.entity;

import java.util.List;
import java.util.Objects;

public class CertificateSearchParams {

    private String name;
    private String description;
    private List<String> tags;
    private String sort;
    private SortOrder sortOrder;

    public String getName() {
        return name;
    }

    public CertificateSearchParams setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificateSearchParams setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public CertificateSearchParams setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public CertificateSearchParams setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public CertificateSearchParams setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateSearchParams that = (CertificateSearchParams) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(tags, that.tags)
                && Objects.equals(sort, that.sort)
                && Objects.equals(sortOrder, that.sortOrder);
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
                ", sort_order=" + sortOrder +
                '}';
    }
}
