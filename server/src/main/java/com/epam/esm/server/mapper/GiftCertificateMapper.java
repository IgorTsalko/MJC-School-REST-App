package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.server.entity.*;

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
        GiftCertificate giftCertificate = new GiftCertificate()
                .setTitle(giftCertificateUpdateRequest.getTitle())
                .setDescription(giftCertificateUpdateRequest.getDescription())
                .setPrice(giftCertificateUpdateRequest.getPrice())
                .setDuration(giftCertificateUpdateRequest.getDuration());

        if (giftCertificateUpdateRequest.getTags() != null) {
            giftCertificate.setTags(TagMapper.convertToEntity(giftCertificateUpdateRequest.getTags()));
        }

        return giftCertificate;
    }

    public static GiftCertificate convertToEntity(GiftCertificateCreateRequest giftCertificateCreateRequest) {
        GiftCertificate giftCertificate = new GiftCertificate()
                .setTitle(giftCertificateCreateRequest.getTitle())
                .setDescription(giftCertificateCreateRequest.getDescription())
                .setPrice(giftCertificateCreateRequest.getPrice())
                .setDuration(giftCertificateCreateRequest.getDuration());

        if (giftCertificateCreateRequest.getTags() != null) {
            giftCertificate.setTags(TagMapper.convertToEntity(giftCertificateCreateRequest.getTags()));
        }

        return giftCertificate;
    }
}
