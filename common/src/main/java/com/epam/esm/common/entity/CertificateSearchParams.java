package com.epam.esm.common.entity;

import com.epam.esm.common.sorting.CertificateSorting;

import java.util.List;
import java.util.Objects;

public class CertificateSearchParams {

    private String title;
    private String description;
    private List<String> tagTitles;
    private List<CertificateSorting> sorting;

    public String getTitle() {
        return title;
    }

    public CertificateSearchParams setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificateSearchParams setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getTagTitles() {
        return tagTitles;
    }

    public CertificateSearchParams setTagTitles(List<String> tagTitles) {
        this.tagTitles = tagTitles;
        return this;
    }

    public List<CertificateSorting> getSorting() {
        return sorting;
    }

    public CertificateSearchParams setSorting(List<CertificateSorting> sorting) {
        this.sorting = sorting;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateSearchParams that = (CertificateSearchParams) o;
        return Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(tagTitles, that.tagTitles)
                && Objects.equals(sorting, that.sorting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tagTitles, sorting);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tagTitles +
                ", sorting=" + sorting +
                '}';
    }
}
