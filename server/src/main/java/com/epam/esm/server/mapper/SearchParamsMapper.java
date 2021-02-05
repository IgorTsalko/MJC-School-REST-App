package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.common.sorting.CertificateColumn;
import com.epam.esm.common.sorting.CertificateSorting;
import com.epam.esm.common.sorting.SortOrder;
import com.epam.esm.server.entity.CertificateSearchParamsRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchParamsMapper {

    public static CertificateSearchParams convertToEntity(CertificateSearchParamsRequest searchParamsRequest) {
        CertificateSearchParams searchParams = new CertificateSearchParams()
                .setTitle(searchParamsRequest.getTitle())
                .setDescription(searchParamsRequest.getDescription());

        if (searchParamsRequest.getTags() != null) {
            List<String> tags = List.of(searchParamsRequest.getTags().split(","));
            tags = tags.stream().map(String::trim).collect(Collectors.toList());
            searchParams.setTagTitles(tags);
        }

        if (searchParamsRequest.getSorting() != null) {
            List<CertificateSorting> sorting = new ArrayList<>();
            List<String> sorts = List.of(searchParamsRequest.getSorting().split(","));
            for (String sort : sorts) {
                CertificateSorting certificateSorting = new CertificateSorting();
                List<String> certSort = Arrays.stream(sort.split(":"))
                        .map(String::trim)
                        .collect(Collectors.toList());
                certificateSorting.setColumn(CertificateColumn.valueOf(certSort.get(0).toUpperCase()));
                if (certSort.size() == 2) {
                    certificateSorting.setSortOrder(SortOrder.valueOf(certSort.get(1).toUpperCase()));
                } else {
                    certificateSorting.setSortOrder(SortOrder.ASC);
                }
                sorting.add(certificateSorting);
            }
            searchParams.setSorting(sorting);
        }

        return searchParams;
    }
}
