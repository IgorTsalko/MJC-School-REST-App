package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.common.entity.SortOrder;
import com.epam.esm.common.entity.Tag;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public void getAllCertificatesWithEmptyParams(@Mock CertificateSearchParams params) {
        List<Certificate> expCerts = List.of(
                new Certificate().setId(1L).setName("Trip").setDescription("Incredible journey. 25 countries. 4 weeks")
                        .setPrice(BigDecimal.valueOf(5600.0)).setDuration(60).setCreateDate(t).setLastUpdateDate(t)
                        .setTags(List.of(new Tag().setId(1L).setName("incredible"))),
                new Certificate().setId(2L).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                        .setCreateDate(t).setLastUpdateDate(t)
                        .setTags(List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(2L).setName("travel"))));
        List<Certificate> actualCerts = certificateRepository.getCertificates(params, 1, 20);
        assertEquals(expCerts, actualCerts);
    }

    @Test
    public void getAllCertificatesWithParams() {
        CertificateSearchParams params = new CertificateSearchParams();
        params.setName("sky");
        params.setSort("id");
        params.setSortOrder(SortOrder.DESC);

        List<Certificate> expCerts =
                List.of(new Certificate().setId(2L).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0))
                .setDuration(30).setCreateDate(t).setLastUpdateDate(t)
                .setTags(List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(2L).setName("travel"))));
        List<Certificate> actualCerts = certificateRepository.getCertificates(params, 1, 20);
        assertEquals(expCerts, actualCerts);
    }

    @Test
    public void getCertificateById() {
        Certificate expCert = new Certificate().setId(2L).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0))
                .setDuration(30).setCreateDate(t).setLastUpdateDate(t)
                .setTags(List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(2L).setName("travel")));
        Certificate actualCert = certificateRepository.get(2L).setTags(tagRepository.getCertificateTags(2L));
        assertEquals(expCert, actualCert);
    }

    @Test
    public void getCertificateByNonExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.get(10L));
    }

    @Test
    @Transactional
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
    @Transactional
    public void updateCertificate() {
        Certificate expCert = new Certificate()
                .setId(2L)
                .setName("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14)
                .setTags(List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(2L).setName("travel")));

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
    @Transactional
    public void updateCertificateByNonExistentId() {
        assertThrows(EntityNotFoundException.class,
                () -> certificateRepository.update(10L, new Certificate()));
    }

    @Test
    @Transactional
    public void deleteCertificate() {
        assertDoesNotThrow(() -> certificateRepository.delete(1L));
    }

    @Test
    @Transactional
    public void deleteCertificateByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.delete(10L));
    }
}
