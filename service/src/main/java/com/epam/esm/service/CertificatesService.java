package com.epam.esm.service;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.repository.CertificatesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificatesService {

    private final CertificatesRepository certificatesRepository;

    public CertificatesService(CertificatesRepository certificatesRepository) {
        this.certificatesRepository = certificatesRepository;
    }

    public List<CertificateDTO> getAllCertificates() {
        return certificatesRepository.getAllCertificates();
    }

    public CertificateDTO getCertificate(int id) {
        return certificatesRepository.getCertificateById(id);
    }

    @Transactional
    public CertificateDTO createNewCertificate(CertificateDTO certificate) {
        return certificatesRepository.saveNewCertificate(certificate);
    }

    @Transactional
    public CertificateDTO updateCertificate(CertificateDTO certificate) {
        return certificatesRepository.updateCertificateById(certificate);
    }

    @Transactional
    public void deleteCertificateById(int id) {
        certificatesRepository.deleteCertificateById(id);
    }
}
