package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll() {
        return tagRepository.getAll();
    }

    public Tag get(Long id) {
        return tagRepository.get(id);
    }

    @Transactional
    public Tag create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.delete(id);
    }
}
