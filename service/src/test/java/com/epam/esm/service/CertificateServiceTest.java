package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
    public void getAllCertificates(@Mock CertificateSearchParams paramsMock) {
        List<Certificate> expCerts = getMockCertificates();
        Map<Long, List<Tag>> expTags = getMockCertificatesTags();
        expCerts.forEach(c -> c.setTags(expTags.get(c.getId())));

        List<Certificate> certs = getMockCertificates();
        Map<Long, List<Tag>> tags = getMockCertificatesTags();
        certs.forEach(c -> c.setTags(tags.get(c.getId())));
        when(certificateRepository.retrieveCertificates(paramsMock, 1, 20)).thenReturn(certs);
        List<Certificate> actualCerts = certificateService.getCertificates(paramsMock, 1, 20);

        assertEquals(expCerts, actualCerts);
        verify(certificateRepository, only()).retrieveCertificates(paramsMock, 1, 20);
    }

    @Test
    public void getCertificateById() {
        Certificate expCert = new Certificate().setId(2L).setTitle("Spa");
        List<Tag> tags = getMockTags();
        expCert.setTags(tags);
        Certificate cert = new Certificate().setId(2L).setTitle("Spa");

        when(tagRepository.retrieveCertificateTags(anyLong())).thenReturn(tags);
        when(certificateRepository.findById(anyLong())).thenReturn(cert);
        Certificate actualCert = certificateService.findById(anyLong());

        assertEquals(expCert, actualCert);
        verify(tagRepository, only()).retrieveCertificateTags(anyLong());
        verify(certificateRepository, only()).findById(anyLong());
    }

    @Test
    public void createNewCertificateWithoutTags(@Mock Certificate certMock) {
        Certificate expCert = new Certificate().setId(2L).setTitle("Spa");
        List<Tag> expTags = getMockTagsWithoutId();
        expCert.setTags(expTags);

        when(certificateRepository.save(certMock)).thenReturn(expCert);
        Certificate actualCert = certificateService.create(certMock);

        assertEquals(expCert, actualCert);
        verify(certificateRepository).save(certMock);
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

        when(tagRepository.saveNonExistent(anyList())).thenReturn(expTags);
        when(tagRepository.retrieveCertificateTags(anyLong())).thenReturn(expTags);
        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        Certificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(tagRepository).saveNonExistent(anyList());
        verify(tagRepository).retrieveCertificateTags(anyLong());
        verify(certificateRepository).update(1L, certMock);
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithEmptyTags(@Mock Certificate certMock) {
        Certificate expCert = getMockCertificate();
        List<Tag> expTags = getMockTags();
        expCert.setTags(expTags);

        Certificate cert = getMockCertificate();
        List<Tag> tags = new ArrayList<>();
        cert.setTags(tags);

        when(tagRepository.retrieveCertificateTags(anyLong())).thenReturn(expTags);
        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        Certificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(tagRepository).retrieveCertificateTags(anyLong());
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

        when(tagRepository.retrieveCertificateTags(anyLong())).thenReturn(tags);
        when(certificateRepository.update(eq(1L), any(Certificate.class))).thenReturn(cert);
        Certificate actualCert = certificateService.update(1L, cert);

        assertEquals(expCert, actualCert);

        verify(tagRepository, only()).retrieveCertificateTags(anyLong());
        verify(certificateRepository, only()).update(eq(1L), any(Certificate.class));
    }

    @Test
    public void deleteCertificate() {
        certificateService.delete(anyLong());
        verify(certificateRepository, only()).delete(anyLong());
    }

    private List<Certificate> getMockCertificates() {
        return List.of(
                new Certificate().setId(1L).setTitle("Trip around the world"),
                new Certificate().setId(2L).setTitle("Spa"),
                new Certificate().setId(5L).setTitle("Sailing")
        );
    }

    private Map<Long, List<Tag>> getMockCertificatesTags() {
        return Map.of(
                1L, List.of(new Tag().setId(1L).setTitle("incredible"), new Tag().setId(3L).setTitle("pleasure")),
                5L, List.of(new Tag().setId(1L).setTitle("incredible"), new Tag().setId(4L).setTitle("jump"))
        );
    }

    private List<Tag> getMockTags() {
        return List.of(
                new Tag().setId(1L).setTitle("incredible"),
                new Tag().setId(2L).setTitle("pleasure")
        );
    }

    private List<Tag> getMockTagsWithoutId() {
        return List.of(
                new Tag().setTitle("incredible"),
                new Tag().setTitle("pleasure")
        );
    }

    private Certificate getMockCertificate() {
        return new Certificate()
                .setId(1L)
                .setTitle("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100);
    }
}
