package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.TagDTO;

import java.util.ArrayList;
import java.util.List;

public class CertificateMapper {

    public static CertificateResponse dtoToResponse(CertificateDTO certificateDTO) {
        CertificateResponse certificateResponse = new CertificateResponse()
                .setId(certificateDTO.getId())
                .setName(certificateDTO.getName())
                .setDescription(certificateDTO.getDescription())
                .setPrice(certificateDTO.getPrice())
                .setDuration(certificateDTO.getDuration())
                .setCreateDate(certificateDTO.getCreateDate())
                .setLastUpdateDate(certificateDTO.getLastUpdateDate());

        List<TagResponse> tagResponses = new ArrayList<>();
        for (TagDTO tagDTO : certificateDTO.getTags()) {
            tagResponses.add(new TagResponse().setId(tagDTO.getId()).setName(tagDTO.getName()));
        }
        certificateResponse.setTags(tagResponses);

        return certificateResponse;
    }

    public static CertificateDTO requestToDto(CertificateRequest certificateRequest) {
        CertificateDTO certificateDTO = new CertificateDTO()
                .setId(certificateRequest.getId())
                .setName(certificateRequest.getName())
                .setDescription(certificateRequest.getDescription())
                .setPrice(certificateRequest.getPrice())
                .setDuration(certificateRequest.getDuration());

        List<TagDTO> tagDTOList = new ArrayList<>();
        for (TagRequest tagRequest : certificateRequest.getTags()) {
            tagDTOList.add(new TagDTO().setId(tagRequest.getId()).setName(tagRequest.getName()));
        }
        certificateDTO.setTags(tagDTOList);

        return certificateDTO;
    }
}
