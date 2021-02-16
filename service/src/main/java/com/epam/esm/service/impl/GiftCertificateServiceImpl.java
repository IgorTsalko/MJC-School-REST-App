package com.epam.esm.service.impl;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateParams params, int page, int limit) {
        return giftCertificateRepository.getGiftCertificates(params, page, limit);
    }

    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.createNonExistent(tags);
            giftCertificate.setTags(tags);
        }
        return giftCertificateRepository.create(giftCertificate);
    }

    @Override
    public GiftCertificate replace(Long id, GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.createNonExistent(tags);
            giftCertificate.setTags(tags);
        }
        return giftCertificateRepository.replace(id, giftCertificate);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        GiftCertificate updatedGiftCertificate = giftCertificateRepository.update(id, giftCertificate);

        if (giftCertificate.getTags() != null) {
            List<Tag> tags = tagRepository.createNonExistent(giftCertificate.getTags());
            updatedGiftCertificate.setTags(tags);
        }

        return updatedGiftCertificate;
    }

    @Override
    public void delete(Long id) {
        giftCertificateRepository.delete(id);
    }
}
