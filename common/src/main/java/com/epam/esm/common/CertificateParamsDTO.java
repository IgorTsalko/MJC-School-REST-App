package com.epam.esm.common;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CertificateParamsDTO {

    @Size(min = 2, max = 50)
    private String name;
    @Size(min = 2, max = 50)
    private String tag;
    @Size(min = 3, max = 20)
    private String sort;
    @Size(min = 3, max = 4)
    private String sort_order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateParamsDTO certificateParamsDTO = (CertificateParamsDTO) o;
        return Objects.equals(name, certificateParamsDTO.name)
                && Objects.equals(tag, certificateParamsDTO.tag)
                && Objects.equals(sort, certificateParamsDTO.sort)
                && Objects.equals(sort_order, certificateParamsDTO.sort_order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tag, sort, sort_order);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", sort='" + sort + '\'' +
                ", sort_order='" + sort_order + '\'' +
                '}';
    }
}
