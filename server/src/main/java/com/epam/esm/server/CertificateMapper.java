package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;

public class CertificateMapper {

    public static CertificateResponse dtoToResponse(CertificateDTO certificateDTO) {
        return new CertificateResponse()
                .setId(certificateDTO.getId())
                .setName(certificateDTO.getName())
                .setDescription(certificateDTO.getDescription())
                .setPrice(certificateDTO.getPrice())
                .setDuration(certificateDTO.getDuration())
                .setCreateDate(certificateDTO.getCreateDate())
                .setLastUpdateDate(certificateDTO.getLastUpdateDate())
                .setTags(certificateDTO.getTags());
    }

    public static CertificateDTO requestToDto(CertificateRequest certificateRequest) {
        return new CertificateDTO()
                .setId(certificateRequest.getId())
                .setName(certificateRequest.getName())
                .setDescription(certificateRequest.getDescription())
                .setPrice(certificateRequest.getPrice())
                .setDuration(certificateRequest.getDuration())
                .setTags(certificateRequest.getTags());
    }
}
