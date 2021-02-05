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

@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Certificate> getCertificates(CertificateSearchParams params, int page, int limit) {
        return certificateRepository.getCertificates(params, page, limit);
    }

    @Override
    public Certificate get(Long id) {
        return certificateRepository.get(id);
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
        return certificateRepository.put(id, certificate);
    }

    @Override
    public Certificate update(Long id, Certificate certificate) {
        Certificate updatedCertificate = certificateRepository.update(id, certificate);

        if (certificate.getTags() != null) {
            List<Tag> tags = tagRepository.createNonExistent(certificate.getTags());
            updatedCertificate.setTags(tags);
        }

        return updatedCertificate;
    }

    @Override
    public void delete(Long id) {
        certificateRepository.delete(id);
    }
}
