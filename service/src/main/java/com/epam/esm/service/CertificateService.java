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

    public CertificateDTO getCertificate(int id) {
        return certificateRepository.getCertificateById(id);
    }

    @Transactional
    public CertificateDTO createNewCertificate(CertificateDTO certificate) {
        certificate = certificateRepository.saveNewCertificate(certificate);

        List<TagDTO> tags = certificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tagRepository.saveTagsIfNonExist(tags);
            certificate.setTags(tagRepository.getTagsByName(tags));
            certificate = certificateRepository.addCertificateTagConnections(certificate);
        }

        return certificate;
    }

    @Transactional
    public CertificateDTO updateCertificate(CertificateDTO certificate) {
        certificate = certificateRepository.updateCertificateById(certificate);
        certificateRepository.deleteCertificateTagConnections(certificate.getId());

        List<TagDTO> tags = certificate.getTags();
        if (tags != null) {
            if (tags.isEmpty()) {
                certificate.setTags(null);
            } else {
                tagRepository.saveTagsIfNonExist(tags);
                certificate.setTags(tagRepository.getTagsByName(tags));
                certificate = certificateRepository.addCertificateTagConnections(certificate);
            }
        } else {
            certificate.setTags(certificateRepository.getCertificateTagConnections(certificate.getId()));
        }

        return certificate;
    }

    @Transactional
    public void deleteCertificateById(int id) {
        certificateRepository.deleteCertificateTagConnections(id);
        certificateRepository.deleteCertificateById(id);
    }
}
