package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return tagRepository
                .findAll(PageRequest.of(page - 1, limit, Sort.by("id")))
                .getContent();
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id));
    }

    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        tagRepository.deleteById(id);
    }

    @Override
    public Tag findMostUsedTagForUserWithHighestCostOfAllOrders() {
        return tagRepository.findMostUsedTagForUserWithHighestCostOfAllOrders();
    }
}
