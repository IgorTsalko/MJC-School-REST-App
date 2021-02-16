package com.epam.esm.service.impl;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.repository.GiftCertificateRepositoryOld;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    //this will be removed later
    private final GiftCertificateRepositoryOld giftCertificateRepositoryOld;

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepositoryOld giftCertificateRepositoryOld,
                                      GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository) {
        this.giftCertificateRepositoryOld = giftCertificateRepositoryOld;
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateParams params, int page, int limit) {
        //todo: this is in progress
        return giftCertificateRepositoryOld.getGiftCertificates(params, page, limit);
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, id));
        Hibernate.initialize(giftCertificate.getTags());
        return giftCertificate;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.saveIfNotExist(tags);
            giftCertificate.setTags(tags);
        }
        return giftCertificateRepository.save(giftCertificate);
    }

    @Override
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
    public void delete(Long id) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, id);
        }
        giftCertificateRepository.deleteById(id);
    }
}
