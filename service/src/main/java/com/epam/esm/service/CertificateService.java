package com.epam.esm.service;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    public CertificateService(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    public List<CertificateDTO> getAllCertificates() {
        return certificateRepository.getAllCertificates();
    }

    @Transactional
    public CertificateDTO getCertificate(int id) {
        return certificateRepository.getCertificate(id).setTags(tagRepository.getCertificateTags(id));
    }

    @Transactional
    public CertificateDTO createNewCertificate(CertificateDTO certificate) {
        certificate = certificateRepository.saveNewCertificate(certificate);

        List<TagDTO> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tagRepository.saveTagsIfNonExist(tags);
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
                tagRepository.saveTagsIfNonExist(tags);
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
        certificateRepository.deleteCertificateTagConnections(id);
        certificateRepository.deleteCertificate(id);
    }
}
