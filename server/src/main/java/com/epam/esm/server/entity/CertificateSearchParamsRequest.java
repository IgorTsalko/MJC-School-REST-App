package com.epam.esm.server.entity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CertificateSearchParamsRequest {

    @Size(min = 1, max = 255)
    private String title;
    @Size(min = 1, max = 250)
    private String description;
    @Size(min = 2)
    private String tags;
    @Pattern(regexp = "((id|title|description|price|duration|create_date)+:?(asc\\b|desc\\b)?,?)+")
    private String sorting;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateSearchParamsRequest that = (CertificateSearchParamsRequest) o;
        return Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(tags, that.tags)
                && Objects.equals(sorting, that.sorting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tags, sorting);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", sorting='" + sorting + '\'' +
                '}';
    }
}
