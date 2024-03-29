package com.epam.esm.common.filtering;

import com.epam.esm.common.sorting.Sorting;

import java.util.List;
import java.util.Objects;

public class GiftCertificateFilteringParams {

    private String title;
    private String description;
    private List<String> tagTitles;
    private List<Sorting> sorts;

    public String getTitle() {
        return title;
    }

    public GiftCertificateFilteringParams setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GiftCertificateFilteringParams setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getTagTitles() {
        return tagTitles;
    }

    public GiftCertificateFilteringParams setTagTitles(List<String> tagTitles) {
        this.tagTitles = tagTitles;
        return this;
    }

    public List<Sorting> getSorts() {
        return sorts;
    }

    public GiftCertificateFilteringParams setSorts(List<Sorting> sorts) {
        this.sorts = sorts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateFilteringParams that = (GiftCertificateFilteringParams) o;
        return Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(tagTitles, that.tagTitles)
                && Objects.equals(sorts, that.sorts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tagTitles, sorts);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tagTitles +
                ", sorts=" + sorts +
                '}';
    }
}
