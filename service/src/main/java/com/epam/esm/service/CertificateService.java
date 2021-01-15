package com.epam.esm.service;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.SearchParams;

import java.util.List;

public interface CertificateService {

    List<CertificateDTO> getCertificates(SearchParams params);

    CertificateDTO getCertificate(int id);

    CertificateDTO createNewCertificate(CertificateDTO certificate);

    CertificateDTO updateCertificate(int certificateId, CertificateDTO certificate);

    void deleteCertificate(int id);
}
