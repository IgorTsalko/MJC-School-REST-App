package com.epam.esm.service.impl;

import com.epam.esm.common.Tag;
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

    public List<Tag> getAllTags() {
        return tagRepository.getAllTags();
    }

    public Tag getTag(Long id) {
        return tagRepository.getTag(id);
    }

    public Tag createNewTag(Tag tag) {
        return tagRepository.createNewTag(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteTag(id);
    }
}
