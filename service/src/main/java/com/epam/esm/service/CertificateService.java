package com.epam.esm.service;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        List<TagDTO> tags = certificate.getTags();

        if (tags.size() > 0) {
            // retrieve all tags that exist among sent tags.
            List<TagDTO> existingTags = tagRepository.getTagsByName(tags);

            // if some tags do not exist, have to create them
            if (existingTags.size() != tags.size()) {
                List<String> sentTagsName = tags.stream().map(TagDTO::getName).collect(Collectors.toList());
                List<String> existingTagsName = existingTags.stream().map(TagDTO::getName).collect(Collectors.toList());
                List<String> nonexistentTagsName = sentTagsName.stream()
                        .filter(tagName -> !existingTagsName.contains(tagName))
                        .collect(Collectors.toList());

                tagRepository.saveNewTags(nonexistentTagsName);

                // again retrieve all sent tags in order to get their id
                tags = tagRepository.getTagsByName(certificate.getTags());
            } else {
                tags = existingTags;
            }

            certificate.setTags(tags);
        }

        // add new certificate
        certificate = certificateRepository.saveNewCertificate(certificate);

        // add certificate tag connections
        if (tags.size() > 0) {
            certificate = certificateRepository.addCertificateTagConnections(certificate);
        }

        return certificate;
    }

    @Transactional
    public CertificateDTO updateCertificate(CertificateDTO certificate) {
        return certificateRepository.updateCertificateById(certificate);
    }

    @Transactional
    public void deleteCertificateById(int id) {
        certificateRepository.deleteCertificateById(id);
    }
}
