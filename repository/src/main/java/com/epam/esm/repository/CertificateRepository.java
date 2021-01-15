package com.epam.esm.repository;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.TagDTO;

import java.util.List;

public interface CertificateRepository {

    List<CertificateDTO> getCertificates(SearchParams params);

    CertificateDTO getCertificate(int id);

    CertificateDTO createNewCertificate(CertificateDTO certificate);

    CertificateDTO updateCertificate(int certificateId, CertificateDTO certificate);

    void deleteCertificate(int id);

    void addCertificateTagConnections(int certificateId, List<TagDTO> tags);

    void deleteCertificateTagConnections(int certificateId);
}
