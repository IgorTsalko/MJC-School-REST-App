package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.server.entity.CertificateSearchParamsRequest;

import java.util.List;
import java.util.stream.Collectors;

public class SearchParamsMapper {

    public static CertificateSearchParams convertToEntity(CertificateSearchParamsRequest searchParamsRequest) {
        CertificateSearchParams searchParams = new CertificateSearchParams()
                .setTitle(searchParamsRequest.getTitle())
                .setDescription(searchParamsRequest.getDescription())
                .setSort(searchParamsRequest.getSort())
                .setSortOrder(searchParamsRequest.getSortOrder());

        if (searchParamsRequest.getTags() != null) {
            List<String> tags = List.of(searchParamsRequest.getTags().split(","));
            tags = tags.stream().map(String::trim).collect(Collectors.toList());
            searchParams.setTags(tags);
        }

        return searchParams;
    }
}
