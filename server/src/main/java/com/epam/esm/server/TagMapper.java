package com.epam.esm.server;

import com.epam.esm.common.Tag;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TagMapper {

    public static TagResponse convertToResponse(Tag tag) {
        return new TagResponse().setId(tag.getId()).setName(tag.getName());
    }

    public static Tag convertToEntity(TagRequest tagRequest) {
        return new Tag().setId(tagRequest.getId()).setName(tagRequest.getName());
    }

    public static List<TagResponse> convertToResponse (List<Tag> tags) {
        return tags.stream().map(TagMapper::convertToResponse).collect(Collectors.toList());
    }

    public static List<Tag> convertToEntity (List<TagRequest> tags) {
        return tags.stream().map(TagMapper::convertToEntity).collect(Collectors.toList());
    }
}
