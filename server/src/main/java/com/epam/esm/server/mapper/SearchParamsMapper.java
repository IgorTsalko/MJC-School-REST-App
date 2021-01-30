package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.server.entity.CertificateSearchParamsRequest;

import java.util.List;

public class SearchParamsMapper {

    public static CertificateSearchParams convertToEntity(CertificateSearchParamsRequest searchParamsRequest) {
        CertificateSearchParams searchParams = new CertificateSearchParams()
                .setName(searchParamsRequest.getName())
                .setDescription(searchParamsRequest.getDescription())
                .setSort(searchParamsRequest.getSort())
                .setSortOrder(searchParamsRequest.getSortOrder());

        if (searchParamsRequest.getTags() != null) {
            searchParams.setTags(List.of(searchParamsRequest.getTags().split("\\s*,\\s*")));
        }

        return searchParams;
    }
}
