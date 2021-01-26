package com.epam.esm.service.impl;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public List<Certificate> getAll(SearchParams params) {
        return certificateRepository.getAll(params);
    }

    @Transactional
    public Certificate get(Long id) {
        return certificateRepository.get(id)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Transactional
    public Certificate create(Certificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.createNonExistentTags(tags);
            certificate.setTags(tags);
        }
        return certificateRepository.create(certificate);
    }

    @Transactional
    public Certificate put(Long id, Certificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.createNonExistentTags(tags);
            certificate.setTags(tags);
        }
        return certificateRepository.upsert(id, certificate);
    }

    @Transactional
    public Certificate update(Long id, Certificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (tags != null) {
            tags = tagRepository.createNonExistentTags(tags);
            certificate.setTags(tags);
        }
        return certificateRepository.get(id)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Transactional
    public void delete(Long id) {
        certificateRepository.delete(id);
    }
}
