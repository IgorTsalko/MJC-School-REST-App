package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.SearchParams;
import com.epam.esm.repository.config.RepositoryConfigTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration(classes = RepositoryConfigTest.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class CertificateRepositoryTest {

    static LocalDateTime t = LocalDateTime.parse("2021-01-13T18:27:45.610874");

    @Autowired
    CertificateRepository certificateRepository;

    @Test
    public void getAllCertificates(@Mock SearchParams prams) {
        List<CertificateDTO> expCerts = List.of(
                new CertificateDTO().setId(1).setName("Trip").setDescription("Incredible journey. 25 countries. 4 weeks")
                        .setPrice(BigDecimal.valueOf(5600.0)).setDuration(60).setCreateDate(t).setLastUpdateDate(t),
                new CertificateDTO().setId(2).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0)).setDuration(30)
                        .setCreateDate(t).setLastUpdateDate(t)
        );
        List<CertificateDTO> realCerts = certificateRepository.getCertificates(prams);

        assertEquals(expCerts, realCerts);
    }

    @Test
    public void getCertificateById() {
        CertificateDTO expCert = new CertificateDTO().setId(2).setName("Skydiving").setPrice(BigDecimal.valueOf(250.0))
                .setDuration(30).setCreateDate(t).setLastUpdateDate(t);
        CertificateDTO realCert = certificateRepository.getCertificate(2);

        assertEquals(expCert, realCert);
    }
}
