package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.server.entity.*;

import java.math.BigDecimal;
import java.util.List;

public class CertificateMapper {

    public static CertificateResponse convertToResponse(Certificate certificate) {
        CertificateResponse certificateResponse = new CertificateResponse()
                .setId(certificate.getId())
                .setTitle(certificate.getTitle())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration())
                .setCreateDate(certificate.getCreateDate())
                .setLastUpdateDate(certificate.getLastUpdateDate());

        if (certificate.getTags() != null) {
            certificateResponse.setTags(TagMapper.convertToResponse(certificate.getTags()));
        }

        return certificateResponse;
    }

    public static Certificate convertToEntity(CertificateUpdateRequest certificateUpdateRequest) {
        return createCertificate(
                certificateUpdateRequest.getId(),
                certificateUpdateRequest.getTitle(),
                certificateUpdateRequest.getDescription(),
                certificateUpdateRequest.getPrice(),
                certificateUpdateRequest.getDuration(),
                certificateUpdateRequest.getTags());
    }

    public static Certificate convertToEntity(CertificateCreateRequest certificateCreateRequest) {
        return createCertificate(
                certificateCreateRequest.getId(),
                certificateCreateRequest.getTitle(),
                certificateCreateRequest.getDescription(),
                certificateCreateRequest.getPrice(),
                certificateCreateRequest.getDuration(),
                certificateCreateRequest.getTags()
        );
    }

    private static Certificate createCertificate(
            Long id,
            String name,
            String description,
            BigDecimal price,
            Integer duration,
            List<TagRequest> tags) {

        Certificate certificate = new Certificate()
                .setId(id)
                .setTitle(name)
                .setDescription(description)
                .setPrice(price)
                .setDuration(duration);

        if (tags != null) {
            certificate.setTags(TagMapper.convertToEntity(tags));
        }

        return certificate;
    }
}
