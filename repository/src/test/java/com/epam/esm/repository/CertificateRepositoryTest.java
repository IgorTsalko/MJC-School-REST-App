package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.config.RepositoryConfigTest;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = RepositoryConfigTest.class)
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class CertificateRepositoryTest {

    static LocalDateTime t = LocalDateTime.parse("2021-01-13T18:27:45.610874");

    @Autowired
    CertificateRepositoryImpl certificateRepository;
    @Autowired
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllCertificatesWithEmptyParams(@Mock SearchParams params) {
        List<Certificate> expCerts = List.of(
                new Certificate().setId(1L).setName("Trip").setDescription("Incredible journey. 25 countries. 4 weeks")
                        .setPrice(BigDecimal.valueOf(5600.0)).setDuration(60).setCreateDate(t).setLastUpdateDate(t),
                new Certificate().setId(2L).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                        .setCreateDate(t).setLastUpdateDate(t)
        );
        List<Certificate> realCerts = certificateRepository.getAll(params);

        assertEquals(expCerts, realCerts);
    }

    @Test
    public void getAllCertificatesWithParams() {
        SearchParams params = new SearchParams();
        params.setName("sky");
        params.setSort("id");
        params.setSort_order(SearchParams.SortOrder.DESC);

        List<Certificate> expCerts = List.of(
                new Certificate().setId(2L).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                        .setCreateDate(t).setLastUpdateDate(t)
        );

        List<Certificate> realCerts = certificateRepository.getAll(params);

        assertEquals(expCerts, realCerts);
    }

    @Test
    public void getCertificateById() {
        Certificate expCert = new Certificate().setId(2L).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0))
                .setDuration(30).setCreateDate(t).setLastUpdateDate(t);
        Certificate realCert = certificateRepository.get(2L);

        assertEquals(expCert, realCert);
    }

    @Test
    public void getCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.get(10L));
    }

    @Test
    public void createNewCertificate() {
        Certificate expCert = new Certificate()
                .setId(3L)
                .setName("NewCertificate")
                .setPrice(BigDecimal.valueOf(178.0))
                .setDuration(14);

        Certificate cert = new Certificate()
                .setName("NewCertificate")
                .setPrice(BigDecimal.valueOf(178.0))
                .setDuration(14);

        Certificate actualCert = certificateRepository.create(cert);
        expCert.setCreateDate(actualCert.getCreateDate());
        expCert.setLastUpdateDate(actualCert.getLastUpdateDate());

        assertEquals(expCert, actualCert);
    }

    @Test
    public void updateCertificate() {
        Certificate expCert = new Certificate()
                .setId(2L)
                .setName("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14);

        Certificate cert = new Certificate()
                .setName("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14);

        certificateRepository.update(2L, cert);
        Certificate actualCert = certificateRepository.get(2L);
        expCert.setCreateDate(actualCert.getCreateDate());
        expCert.setLastUpdateDate(actualCert.getLastUpdateDate());

        assertEquals(expCert, actualCert);
    }

    @Test
    public void updateCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class,
                () -> certificateRepository.update(10L, new Certificate()));
    }

    @Test
    public void deleteCertificate() {
        assertDoesNotThrow(() -> certificateRepository.delete(1L));
    }

    @Test
    public void deleteCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.delete(10L));
    }

    @Test
    public void addCertificateTagConnections() {
        List<Tag> expTags = List.of(
                new Tag().setId(1L).setName("incredible"),
                new Tag().setId(2L).setName("travel")
        );
        List<Tag> tags = List.of(
                new Tag().setId(2L).setName("travel")
        );
        certificateRepository.addCertificateTagConnections(1L, tags);
        List<Tag> actualTags = tagRepository.getCertificateTags(1L);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void deleteCertificateTagConnections() {
        certificateRepository.deleteCertificateTagConnections(2L);
        List<Tag> expTags = new ArrayList<>();
        List<Tag> actualTags = tagRepository.getCertificateTags(2L);
        assertEquals(expTags, actualTags);
    }
}
