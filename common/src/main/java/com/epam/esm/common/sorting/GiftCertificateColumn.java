package com.epam.esm.common.sorting;

public enum GiftCertificateColumn implements Column {

    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("createDate");

    private final String columnTitle;

    GiftCertificateColumn(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    @Override
    public String getColumnTitle() {
        return columnTitle;
    }
}
