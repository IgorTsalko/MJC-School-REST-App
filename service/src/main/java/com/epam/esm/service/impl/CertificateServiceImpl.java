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
import java.util.Map;

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
        List<Certificate> certificates = certificateRepository.getAll(params);
        Map<Long, List<Tag>> certificateTags = tagRepository.getCertificatesTags(certificates);
        certificates.forEach(certificate -> certificate.setTags(certificateTags.get(certificate.getId())));
        return certificates;
    }

    @Transactional
    public Certificate get(Long id) {
        return certificateRepository.get(id)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Transactional
    public Certificate create(Certificate certificate) {
        certificate = certificateRepository.create(certificate);

        List<Tag> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tagRepository.createTagsIfNonExist(tags);
            tags = tagRepository.getTagsByName(tags);
            certificateRepository.addCertificateTagConnections(certificate.getId(), tags);
            certificate.setTags(tags);
        }

        return certificate;
    }

    @Transactional
    public Certificate upsert(Long id, Certificate certificate) {
        certificate = certificateRepository.upsert(id, certificate);

        List<Tag> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tagRepository.createTagsIfNonExist(tags);
            tags = tagRepository.getTagsByName(tags);
            certificateRepository.addCertificateTagConnections(certificate.getId(), tags);
            certificate.setTags(tags);
        }

        return certificate;
    }

    @Transactional
    public Certificate update(Long id, Certificate certificate) {
        certificate = certificateRepository.update(id, certificate);

        List<Tag> tags = certificate.getTags();
        if (tags != null) {
            certificateRepository.deleteCertificateTagConnections(id);
            if (tags.isEmpty()) {
                certificate.setTags(null);
            } else {
                tagRepository.createTagsIfNonExist(tags);
                tags = tagRepository.getTagsByName(tags);
                certificateRepository.addCertificateTagConnections(id, tags);
                certificate.setTags(tags);
            }
        } else {
            certificate.setTags(tagRepository.getCertificateTags(id));
        }

        return certificate;
    }

    @Transactional
    public void delete(Long id) {
        certificateRepository.delete(id);
    }
}
