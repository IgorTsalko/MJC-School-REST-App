package com.epam.esm.service;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.repository.CertificatesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificatesService {

    private final CertificatesRepository certificatesRepository;

    public CertificatesService(CertificatesRepository certificatesRepository) {
        this.certificatesRepository = certificatesRepository;
    }

    public List<CertificateDTO> allCertificates() {
        return certificatesRepository.getAllCertificates();
    }

    public CertificateDTO getCertificate(int id) {
        return certificatesRepository.getCertificateById(id);
    }

    public void createNewCertificate(CertificateDTO certificate) {
        certificatesRepository.saveNewCertificate(certificate);
    }

    public void updateCertificateById(CertificateDTO certificate) {
        certificatesRepository.updateCertificateById(certificate);
    }

    public void deleteCertificateById(int id) {
        certificatesRepository.deleteCertificateById(id);
    }
}
