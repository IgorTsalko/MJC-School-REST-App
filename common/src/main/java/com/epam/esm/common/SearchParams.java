package com.epam.esm.common;

import javax.validation.constraints.Size;
import java.util.Objects;

public class SearchParams {

    @Size(min = 2, max = 50)
    private String name;
    @Size(min = 2, max = 250)
    private String description;
    @Size(min = 2, max = 50)
    private String tag;
    @Size(min = 2, max = 20)
    private String sort;
    private SortOrder sort_order;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public SortOrder getSort_order() {
        return sort_order;
    }

    public void setSort_order(SortOrder sort_order) {
        this.sort_order = sort_order;
    }

    public enum SortOrder {
        DESC, ASC
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchParams that = (SearchParams) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(tag, that.tag)
                && Objects.equals(sort, that.sort)
                && Objects.equals(sort_order, that.sort_order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, tag, sort, sort_order);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", sort='" + sort + '\'' +
                ", sort_order='" + sort_order + '\'' +
                '}';
    }
}
