package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getTags(int page, int limit) {
        return tagRepository.getTags(page, limit);
    }

    @Override
    public Tag get(Long id) {
        return tagRepository.get(id);
    }

    @Override
    public Tag create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.delete(id);
    }

    @Override
    public Tag findMostUsedTagForUserWithHighestCostOfAllOrders() {
        return tagRepository.findMostUsedTagForUserWithHighestCostOfAllOrders();
    }
}
