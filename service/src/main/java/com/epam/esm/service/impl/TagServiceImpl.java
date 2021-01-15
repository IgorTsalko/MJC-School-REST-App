package com.epam.esm.service.impl;

import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.getAllTags();
    }

    public TagDTO getTag(int id) {
        return tagRepository.getTag(id);
    }

    public TagDTO createNewTag(TagDTO tag) {
        return tagRepository.createNewTag(tag);
    }

    public void deleteTag(int id) {
        tagRepository.deleteTag(id);
    }
}
