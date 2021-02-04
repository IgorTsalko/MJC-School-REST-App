package com.epam.esm.common.sorting;

import java.util.Objects;

public class CertificateSorting {

    private CertificateColumn column;
    private SortOrder sortOrder;

    public CertificateColumn getColumn() {
        return column;
    }

    public CertificateSorting setColumn(CertificateColumn column) {
        this.column = column;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public CertificateSorting setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateSorting that = (CertificateSorting) o;
        return column == that.column && sortOrder == that.sortOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, sortOrder);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "column=" + column +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
