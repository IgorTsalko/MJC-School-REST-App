package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.server.entity.*;

import java.math.BigDecimal;
import java.util.List;

public class GiftCertificateMapper {

    public static GiftCertificateResponse convertToResponse(GiftCertificate giftCertificate) {
        GiftCertificateResponse giftCertificateResponse = new GiftCertificateResponse()
                .setId(giftCertificate.getId())
                .setTitle(giftCertificate.getTitle())
                .setDescription(giftCertificate.getDescription())
                .setPrice(giftCertificate.getPrice())
                .setDuration(giftCertificate.getDuration())
                .setCreateDate(giftCertificate.getCreateDate())
                .setLastUpdateDate(giftCertificate.getLastUpdateDate());

        if (giftCertificate.getTags() != null) {
            giftCertificateResponse.setTags(TagMapper.convertToResponse(giftCertificate.getTags()));
        }

        return giftCertificateResponse;
    }

    public static GiftCertificate convertToEntity(GiftCertificateUpdateRequest giftCertificateUpdateRequest) {
        return createCertificate(
                giftCertificateUpdateRequest.getTitle(),
                giftCertificateUpdateRequest.getDescription(),
                giftCertificateUpdateRequest.getPrice(),
                giftCertificateUpdateRequest.getDuration(),
                giftCertificateUpdateRequest.getTags());
    }

    public static GiftCertificate convertToEntity(GiftCertificateCreateRequest giftCertificateCreateRequest) {
        return createCertificate(
                giftCertificateCreateRequest.getTitle(),
                giftCertificateCreateRequest.getDescription(),
                giftCertificateCreateRequest.getPrice(),
                giftCertificateCreateRequest.getDuration(),
                giftCertificateCreateRequest.getTags()
        );
    }

    private static GiftCertificate createCertificate(
            String name,
            String description,
            BigDecimal price,
            Integer duration,
            List<TagRequest> tags) {

        GiftCertificate giftCertificate = new GiftCertificate()
                .setTitle(name)
                .setDescription(description)
                .setPrice(price)
                .setDuration(duration);

        if (tags != null) {
            giftCertificate.setTags(TagMapper.convertToEntity(tags));
        }

        return giftCertificate;
    }
}
