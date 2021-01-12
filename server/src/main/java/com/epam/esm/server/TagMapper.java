package com.epam.esm.server;

import com.epam.esm.common.TagDTO;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;

public class TagMapper {

    public static TagResponse dtoToResponse(TagDTO tagDTO) {
        return new TagResponse().setId(tagDTO.getId()).setName(tagDTO.getName());
    }

    public static TagDTO requestToDto(TagRequest tagRequest) {
        return new TagDTO().setId(tagRequest.getId()).setName(tagRequest.getName());
    }
}
