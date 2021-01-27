package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.Tag;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceTest {

    @InjectMocks
    CertificateServiceImpl certificateService;
    @Mock
    CertificateRepositoryImpl certificateRepository;
    @Mock
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllCertificates(@Mock SearchParams paramsMock) {
        List<Certificate> expCerts = getMockCertificates();
        Map<Long, List<Tag>> expTags = getMockCertificatesTags();
        expCerts.forEach(c -> c.setTags(expTags.get(c.getId())));

        List<Certificate> certs = getMockCertificates();
        when(certificateRepository.getAll(paramsMock)).thenReturn(certs);
        when(tagRepository.getCertificatesTags(anyList())).thenReturn(expTags);
        List<Certificate> actualCerts = certificateService.getAll(paramsMock);

        assertEquals(expCerts, actualCerts);
        verify(certificateRepository, only()).getAll(paramsMock);
        verify(tagRepository, only()).getCertificatesTags(anyList());
    }

    @Test
    public void getCertificateById() {
        Certificate expCert = new Certificate().setId(2L).setName("Spa");
        List<Tag> tags = getMockTags();
        expCert.setTags(tags);
        Certificate cert = new Certificate().setId(2L).setName("Spa");

        when(tagRepository.getCertificateTags(anyLong())).thenReturn(tags);
        when(certificateRepository.get(anyLong())).thenReturn(cert);
        Certificate actualCert = certificateService.get(anyLong());

        assertEquals(expCert, actualCert);
        verify(tagRepository, only()).getCertificateTags(anyLong());
        verify(certificateRepository, only()).get(anyLong());
    }

    @Test
    public void createNewCertificate(@Mock Certificate certMock) {
        Certificate expCert = new Certificate().setId(2L).setName("Spa");
        List<Tag> expTags = getMockTagsWithoutId();
        expCert.setTags(expTags);

        List<Tag> tags = getMockTags();
        when(certificateRepository.create(certMock)).thenReturn(expCert);
        Certificate actualCert = certificateService.create(certMock);

        assertEquals(expCert, actualCert);
        verify(certificateRepository).create(certMock);
        verify(tagRepository).createNonExistentTags(anyList());
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithTags(@Mock Certificate certMock) {
        Certificate expCert = getMockCertificate();
        List<Tag> expTags = getMockTags();
        expCert.setTags(expTags);

        Certificate cert = getMockCertificate();
        List<Tag> tags = getMockTagsWithoutId();
        cert.setTags(tags);

        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        Certificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(certificateRepository).update(1L, certMock);
        verify(tagRepository).createNonExistentTags(anyList());
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithEmptyTags(@Mock Certificate certMock) {
        Certificate expCert = getMockCertificate();

        Certificate cert = getMockCertificate();
        List<Tag> tags = new ArrayList<>();
        cert.setTags(tags);

        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        Certificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(certificateRepository).update(1L, certMock);
        verifyNoMoreInteractions(certificateRepository);
    }

    @Test
    public void updateCertificateWithNullTags(@Mock Certificate certMock) {
        Certificate expCert = getMockCertificate();
        List<Tag> expTags = getMockTags();
        expCert.setTags(expTags);

        Certificate cert = getMockCertificate();
        List<Tag> tags = getMockTags();

        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        when(tagRepository.getCertificateTags(anyLong())).thenReturn(tags);
        Certificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(certificateRepository, only()).update(1L, certMock);
        verify(tagRepository, only()).getCertificateTags(anyLong());
    }

    @Test
    public void deleteCertificate() {
        certificateService.delete(anyLong());
        verify(certificateRepository, only()).delete(anyLong());
    }

    private List<Certificate> getMockCertificates() {
        return List.of(
                new Certificate().setId(1L).setName("Trip around the world"),
                new Certificate().setId(2L).setName("Spa"),
                new Certificate().setId(5L).setName("Sailing")
        );
    }

    private Map<Long, List<Tag>> getMockCertificatesTags() {
        return Map.of(
                1L, List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(3L).setName("pleasure")),
                5L, List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(4L).setName("jump"))
        );
    }

    private List<Tag> getMockTags() {
        return List.of(
                new Tag().setId(1L).setName("incredible"),
                new Tag().setId(2L).setName("pleasure")
        );
    }

    private List<Tag> getMockTagsWithoutId() {
        return List.of(
                new Tag().setName("incredible"),
                new Tag().setName("pleasure")
        );
    }

    private Certificate getMockCertificate() {
        return new Certificate()
                .setId(1L)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(LocalDateTime.of(2020, 12, 20, 16, 40))
                .setLastUpdateDate(LocalDateTime.of(2021, 1, 14, 18, 27));
    }
}
