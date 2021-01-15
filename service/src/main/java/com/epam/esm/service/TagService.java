package com.epam.esm.service;

import com.epam.esm.common.TagDTO;

import java.util.List;

public interface TagService {

    List<TagDTO> getAllTags();

    TagDTO getTag(int id);

    TagDTO createNewTag(TagDTO tag);

    void deleteTag(int id);
}
