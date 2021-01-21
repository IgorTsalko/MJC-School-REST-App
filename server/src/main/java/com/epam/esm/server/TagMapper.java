package com.epam.esm.server;

import com.epam.esm.common.Tag;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;

public class TagMapper {

    public static TagResponse convertToResponse(Tag tag) {
        return new TagResponse().setId(tag.getId()).setName(tag.getName());
    }

    public static Tag convertToEntity(TagRequest tagRequest) {
        return new Tag().setId(tagRequest.getId()).setName(tagRequest.getName());
    }
}
