package com.epam.esm.service.impl;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.sorting.SortOrder;
import com.epam.esm.common.sorting.Sorting;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateSpecification;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import org.hibernate.Hibernate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getGiftCertificates(GiftCertificateParams params, int page, int limit) {
        GiftCertificateSpecification specification = new GiftCertificateSpecification(params);
        if (params.getTagTitles() != null) {
            specification.setTags(tagRepository.findAllByTitleIn(params.getTagTitles()));
        }
        Sort sorting = generateSorting(params.getSorts());

        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findAll(specification, PageRequest.of(page - 1, limit, sorting))
                .getContent();
        giftCertificates.forEach(giftCertificate -> Hibernate.initialize(giftCertificate.getTags()));

        return giftCertificates;
    }

    private Sort generateSorting(List<Sorting> sorts) {
        List<Sort.Order> orderBy = new ArrayList<>();

        if (!CollectionUtils.isEmpty(sorts)) {
            for (Sorting sorting : sorts) {
                if (sorting.getSortOrder().equals(SortOrder.DESC)) {
                    orderBy.add(Sort.Order.desc(sorting.getColumn().getColumnTitle()));
                } else {
                    orderBy.add(Sort.Order.asc(sorting.getColumn().getColumnTitle()));
                }
            }
        } else {
            orderBy.add(Sort.Order.asc("id"));
        }

        return Sort.by(orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, id));
        Hibernate.initialize(giftCertificate.getTags());
        return giftCertificate;
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.saveIfNotExist(tags);
            giftCertificate.setTags(tags);
        }
        return giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificate replace(Long id, GiftCertificate giftCertificate) {
        GiftCertificate dataBaseGiftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, id));

        List<Tag> tags = giftCertificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.saveIfNotExist(tags);
            giftCertificate.setTags(tags);
        }

        giftCertificate.setId(id);
        giftCertificate.setCreateDate(dataBaseGiftCertificate.getCreateDate());
        return giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        GiftCertificate dataBaseGiftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, id));

        String title = giftCertificate.getTitle();
        String description = giftCertificate.getDescription();
        BigDecimal price = giftCertificate.getPrice();
        Integer duration = giftCertificate.getDuration();
        List<Tag> tags = giftCertificate.getTags();

        if (title != null) {
            dataBaseGiftCertificate.setTitle(title);
        }
        if (description != null) {
            dataBaseGiftCertificate.setDescription(description);
        }
        if (price != null) {
            dataBaseGiftCertificate.setPrice(price);
        }
        if (duration != null) {
            dataBaseGiftCertificate.setDuration(duration);
        }
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.saveIfNotExist(tags);
            dataBaseGiftCertificate.setTags(tags);
        }

        GiftCertificate updatedGiftCertificate = giftCertificateRepository.save(dataBaseGiftCertificate);
        Hibernate.initialize(updatedGiftCertificate.getTags());
        return updatedGiftCertificate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, id);
        }
        giftCertificateRepository.deleteById(id);
    }
}
