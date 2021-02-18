package com.epam.esm.server.mapper;

import com.epam.esm.common.filtering.GiftCertificateFilteringParams;
import com.epam.esm.common.sorting.GiftCertificateColumn;
import com.epam.esm.common.sorting.Sorting;
import com.epam.esm.common.sorting.SortOrder;
import com.epam.esm.server.entity.GiftCertificateFilteringParamsRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateParamsMapper {

    public static GiftCertificateFilteringParams convertToEntity(GiftCertificateFilteringParamsRequest searchParamsRequest) {
        GiftCertificateFilteringParams searchParams = new GiftCertificateFilteringParams()
                .setTitle(searchParamsRequest.getTitle())
                .setDescription(searchParamsRequest.getDescription());

        if (searchParamsRequest.getTags() != null) {
            List<String> tags = List.of(searchParamsRequest.getTags().split(","));
            tags = tags.stream().map(String::trim).collect(Collectors.toList());
            searchParams.setTagTitles(tags);
        }

        if (searchParamsRequest.getSorting() != null) {
            List<Sorting> sorting = new ArrayList<>();
            List<String> sorts = List.of(searchParamsRequest.getSorting().split(","));
            for (String sort : sorts) {
                Sorting certificateSorting = new Sorting();
                List<String> certSort = Arrays.stream(sort.split(":"))
                        .map(String::trim)
                        .collect(Collectors.toList());
                certificateSorting.setColumn(GiftCertificateColumn.valueOf(certSort.get(0).toUpperCase()));
                if (certSort.size() == 2) {
                    certificateSorting.setSortOrder(SortOrder.valueOf(certSort.get(1).toUpperCase()));
                } else {
                    certificateSorting.setSortOrder(SortOrder.ASC);
                }
                sorting.add(certificateSorting);
            }
            searchParams.setSorts(sorting);
        }

        return searchParams;
    }
}
