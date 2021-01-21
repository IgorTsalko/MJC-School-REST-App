package com.epam.esm.server;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.Tag;
import com.epam.esm.server.entity.CertificateRequest;
import com.epam.esm.server.entity.CertificateResponse;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;

import java.util.ArrayList;
import java.util.List;

public class CertificateMapper {

    public static CertificateResponse convertToResponse(Certificate certificate) {
        CertificateResponse certificateResponse = new CertificateResponse()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration())
                .setCreateDate(certificate.getCreateDate())
                .setLastUpdateDate(certificate.getLastUpdateDate());

        if (certificate.getTags() != null) {
            List<TagResponse> tagResponses = new ArrayList<>();
            for (Tag tag : certificate.getTags()) {
                tagResponses.add(TagMapper.convertToResponse(tag));
            }
            certificateResponse.setTags(tagResponses);
        }

        return certificateResponse;
    }

    public static Certificate convertToEntity(CertificateRequest certificateRequest) {
        Certificate certificate = new Certificate()
                .setId(certificateRequest.getId())
                .setName(certificateRequest.getName())
                .setDescription(certificateRequest.getDescription())
                .setPrice(certificateRequest.getPrice())
                .setDuration(certificateRequest.getDuration());

        if (certificateRequest.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (TagRequest tagRequest : certificateRequest.getTags()) {
                tags.add(TagMapper.convertToEntity(tagRequest));
            }
            certificate.setTags(tags);
        }

        return certificate;
    }
}
