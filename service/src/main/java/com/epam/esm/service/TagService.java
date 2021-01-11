package com.epam.esm.service;

import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.getAllTags();
    }

    public TagDTO getTag(int id) {
        return tagRepository.getTag(id);
    }

    public TagDTO createNewTag(TagDTO tag) {
        return tagRepository.saveNewTag(tag);
    }

    public void deleteTag(int id) {
        tagRepository.deleteTag(id);
    }
}
