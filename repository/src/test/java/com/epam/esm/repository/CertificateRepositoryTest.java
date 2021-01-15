package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.TagDTO;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.config.RepositoryConfigTest;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = RepositoryConfigTest.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CertificateRepositoryTest {

    static LocalDateTime t = LocalDateTime.parse("2021-01-13T18:27:45.610874");

    @Autowired
    CertificateRepositoryImpl certificateRepository;
    @Autowired
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllCertificatesWithEmptyParams(@Mock SearchParams params) {
        List<CertificateDTO> expCerts = List.of(
                new CertificateDTO().setId(1).setName("Trip").setDescription("Incredible journey. 25 countries. 4 weeks")
                        .setPrice(BigDecimal.valueOf(5600.0)).setDuration(60).setCreateDate(t).setLastUpdateDate(t),
                new CertificateDTO().setId(2).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                        .setCreateDate(t).setLastUpdateDate(t)
        );
        List<CertificateDTO> realCerts = certificateRepository.getCertificates(params);

        assertEquals(expCerts, realCerts);
    }

    @Test
    public void getAllCertificatesWithParams() {
        SearchParams params = new SearchParams();
        params.setName("sky");
        params.setSort("id");
        params.setSort_order(SearchParams.SortOrder.DESC);

        List<CertificateDTO> expCerts = List.of(
                new CertificateDTO().setId(2).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                        .setCreateDate(t).setLastUpdateDate(t)
        );

        List<CertificateDTO> realCerts = certificateRepository.getCertificates(params);

        assertEquals(expCerts, realCerts);
    }

    @Test
    public void getCertificateById() {
        CertificateDTO expCert = new CertificateDTO().setId(2).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0))
                .setDuration(30).setCreateDate(t).setLastUpdateDate(t);
        CertificateDTO realCert = certificateRepository.getCertificate(2);

        assertEquals(expCert, realCert);
    }

    @Test
    public void getCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.getCertificate(10));
    }

    @Test
    public void createNewCertificate() {
        CertificateDTO expCert = new CertificateDTO()
                .setId(3)
                .setName("NewCertificate")
                .setPrice(BigDecimal.valueOf(178.0))
                .setDuration(14);

        CertificateDTO cert = new CertificateDTO()
                .setName("NewCertificate")
                .setPrice(BigDecimal.valueOf(178.0))
                .setDuration(14);

        CertificateDTO actualCert = certificateRepository.createNewCertificate(cert);
        expCert.setCreateDate(actualCert.getCreateDate());
        expCert.setLastUpdateDate(actualCert.getLastUpdateDate());

        assertEquals(expCert, actualCert);
    }

    @Test
    public void updateCertificate() {
        CertificateDTO expCert = new CertificateDTO()
                .setId(2)
                .setName("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14);

        CertificateDTO cert = new CertificateDTO()
                .setName("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14);

        certificateRepository.updateCertificate(2, cert);
        CertificateDTO actualCert = certificateRepository.getCertificate(2);
        expCert.setCreateDate(actualCert.getCreateDate());
        expCert.setLastUpdateDate(actualCert.getLastUpdateDate());

        assertEquals(expCert, actualCert);
    }

    @Test
    public void updateCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class,
                () -> certificateRepository.updateCertificate(10, new CertificateDTO()));
    }

    @Test
    public void deleteCertificate() {
        assertDoesNotThrow(() -> certificateRepository.deleteCertificate(1));
    }

    @Test
    public void deleteCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.deleteCertificate(10));
    }

    @Test
    public void addCertificateTagConnections() {
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("travel")
        );
        List<TagDTO> tags = List.of(
                new TagDTO().setId(2).setName("travel")
        );
        certificateRepository.addCertificateTagConnections(1, tags);
        List<TagDTO> actualTags = tagRepository.getCertificateTags(1);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void deleteCertificateTagConnections() {
        certificateRepository.deleteCertificateTagConnections(2);
        List<TagDTO> expTags = new ArrayList<>();
        List<TagDTO> actualTags = tagRepository.getCertificateTags(2);
        assertEquals(expTags, actualTags);
    }
}
