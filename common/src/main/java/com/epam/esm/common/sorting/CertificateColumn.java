package com.epam.esm.common.sorting;

public enum CertificateColumn {

    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("createDate");

    private final String columnTitle;

    CertificateColumn(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public String getColumnTitle() {
        return columnTitle;
    }
}
