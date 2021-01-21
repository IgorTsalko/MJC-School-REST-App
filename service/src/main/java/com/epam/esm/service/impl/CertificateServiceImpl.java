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
    public List<Certificate> getCertificates(SearchParams params) {
        List<Certificate> certificates = certificateRepository.getCertificates(params);
        Map<Long, List<Tag>> certificateTags = tagRepository.getCertificatesTags(certificates);
        certificates.forEach(certificate -> certificate.setTags(certificateTags.get(certificate.getId())));
        return certificates;
    }

    @Transactional
    public Certificate getCertificate(Long id) {
        return certificateRepository.getCertificate(id)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Transactional
    public Certificate createNewCertificate(Certificate certificate) {
        certificate = certificateRepository.createNewCertificate(certificate);

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
    public Certificate updateCertificate(Long certificateId, Certificate certificate) {
        certificate = certificateRepository.updateCertificate(certificateId, certificate);

        List<Tag> tags = certificate.getTags();
        if (tags != null) {
            certificateRepository.deleteCertificateTagConnections(certificateId);
            if (tags.isEmpty()) {
                certificate.setTags(null);
            } else {
                tagRepository.createTagsIfNonExist(tags);
                tags = tagRepository.getTagsByName(tags);
                certificateRepository.addCertificateTagConnections(certificateId, tags);
                certificate.setTags(tags);
            }
        } else {
            certificate.setTags(tagRepository.getCertificateTags(certificateId));
        }

        return certificate;
    }

    @Transactional
    public void deleteCertificate(Long id) {
        certificateRepository.deleteCertificate(id);
    }
}
