package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.sorting.GiftCertificateColumn;
import com.epam.esm.common.sorting.Sorting;
import com.epam.esm.common.sorting.SortOrder;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.config.RepositoryConfigTest;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
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
public class GiftGiftCertificateRepositoryTest {

    static LocalDateTime t = LocalDateTime.parse("2021-01-13T18:27:45.610874");

    @Autowired
    GiftCertificateRepositoryImpl certificateRepository;
    @Autowired
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllCertificatesWithEmptyParams(@Mock GiftCertificateParams params) {
        List<GiftCertificate> expCerts = List.of(getMockCertId1(), getMockCertId2());
        List<GiftCertificate> actualCerts = certificateRepository.getGiftCertificates(params, 1, 20);
        assertEquals(expCerts, actualCerts);
    }

    @Test
    public void getAllCertificatesWithParams() {
        GiftCertificateParams params = new GiftCertificateParams();
        params.setTitle("sky");
        params.setSorts(List.of(
                new Sorting()
                        .setColumn(GiftCertificateColumn.ID)
                        .setSortOrder(SortOrder.DESC))
        );

        List<GiftCertificate> expCerts = List.of(getMockCertId2());
        List<GiftCertificate> actualCerts = certificateRepository.getGiftCertificates(params, 1, 20);
        assertEquals(expCerts, actualCerts);
    }

    @Test
    public void getCertificateById() {
        GiftCertificate expCert = getMockCertId2();
        GiftCertificate actualCert = certificateRepository.findById(2L).setTags(tagRepository.getCertificateTags(2L));
        assertEquals(expCert, actualCert);
    }

    @Test
    public void getCertificateByNonExistentId() {
        assertThrows(EntityNotFoundException.class, () -> certificateRepository.findById(10L));
    }

    @Test
    @Transactional
    public void createNewCertificate() {
        GiftCertificate expCert = new GiftCertificate()
                .setId(3L)
                .setTitle("NewCertificate")
                .setPrice(BigDecimal.valueOf(178.0))
                .setDuration(14);

        GiftCertificate cert = new GiftCertificate()
                .setTitle("NewCertificate")
                .setPrice(BigDecimal.valueOf(178.0))
                .setDuration(14);

        GiftCertificate actualCert = certificateRepository.create(cert);
        expCert.setCreateDate(actualCert.getCreateDate());
        expCert.setLastUpdateDate(actualCert.getLastUpdateDate());

        assertEquals(expCert, actualCert);
    }

    @Test
    @Transactional
    public void updateCertificate() {
        GiftCertificate expCert = new GiftCertificate()
                .setId(2L)
                .setTitle("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14)
                .setTags(List.of(new Tag().setId(1L).setTitle("incredible"), new Tag().setId(2L).setTitle("travel")));

        GiftCertificate cert = new GiftCertificate()
                .setTitle("Skydiving-2")
                .setDescription("New cool description")
                .setPrice(BigDecimal.valueOf(357.0))
                .setDuration(14);

        certificateRepository.update(2L, cert);
        GiftCertificate actualCert = certificateRepository.findById(2L);
        expCert.setCreateDate(actualCert.getCreateDate());
        expCert.setLastUpdateDate(actualCert.getLastUpdateDate());

        assertEquals(expCert, actualCert);
    }

    @Test
    @Transactional
    public void updateCertificateByNonExistentId() {
        assertThrows(EntityNotFoundException.class,
                () -> certificateRepository.update(10L, new GiftCertificate()));
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

    private GiftCertificate getMockCertId1() {
        GiftCertificate cert = new GiftCertificate().setId(1L).setTitle("Trip")
                .setDescription("Incredible journey. 25 countries. 4 weeks")
                .setPrice(BigDecimal.valueOf(5600.0)).setDuration(60)
                .setTags(List.of(new Tag().setId(1L).setTitle("incredible")));
        cert.setCreateDate(t);
        cert.setLastUpdateDate(t);
        return cert;
    }

    private GiftCertificate getMockCertId2() {
        GiftCertificate cert = new GiftCertificate().setId(2L).setTitle("Skydiving")
                .setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                .setTags(List.of(new Tag().setId(1L).setTitle("incredible"), new Tag().setId(2L).setTitle("travel")));
        cert.setCreateDate(t);
        cert.setLastUpdateDate(t);
        return cert;
    }
}
