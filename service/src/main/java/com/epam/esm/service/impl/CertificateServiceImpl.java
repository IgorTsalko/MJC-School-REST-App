package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Transactional
@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Certificate> getAll(CertificateSearchParams params) {
        return certificateRepository.getAll(params);
    }

    @Override
    public Certificate get(Long id) {
        return certificateRepository.get(id)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Override
    public Certificate create(Certificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.createNonExistent(tags);
            certificate.setTags(tags);
        }
        return certificateRepository.create(certificate);
    }

    @Override
    public Certificate put(Long id, Certificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags = tagRepository.createNonExistent(tags);
            certificate.setTags(tags);
        }
        return certificateRepository.upsert(id, certificate)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Override
    public Certificate update(Long id, Certificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (tags != null) {
            tags = tagRepository.createNonExistent(tags);
            certificate.setTags(tags);
        }
        return certificateRepository.update(id, certificate)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Override
    public void delete(Long id) {
        certificateRepository.delete(id);
    }
}
