package com.epam.esm.service.impl;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.TagDTO;
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
    public List<CertificateDTO> getCertificates(SearchParams params) {
        List<CertificateDTO> certificates = certificateRepository.getCertificates(params);
        Map<Integer, List<TagDTO>> certificateTags = tagRepository.getCertificatesTags(certificates);
        certificates.forEach(certificate -> certificate.setTags(certificateTags.get(certificate.getId())));
        return certificates;
    }

    @Transactional
    public CertificateDTO getCertificate(int id) {
        return certificateRepository.getCertificate(id)
                .setTags(tagRepository.getCertificateTags(id));
    }

    @Transactional
    public CertificateDTO createNewCertificate(CertificateDTO certificate) {
        certificate = certificateRepository.createNewCertificate(certificate);

        List<TagDTO> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tagRepository.createTagsIfNonExist(tags);
            tags = tagRepository.getTagsByName(tags);
            certificateRepository.addCertificateTagConnections(certificate.getId(), tags);
            certificate.setTags(tags);
        }

        return certificate;
    }

    @Transactional
    public CertificateDTO updateCertificate(int certificateId, CertificateDTO certificate) {
        certificate = certificateRepository.updateCertificate(certificateId, certificate);

        List<TagDTO> tags = certificate.getTags();
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
    public void deleteCertificate(int id) {
        certificateRepository.deleteCertificate(id);
    }
}
