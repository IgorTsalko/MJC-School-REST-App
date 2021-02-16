package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
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
public class GiftGiftCertificateServiceTest {

    @InjectMocks
    GiftCertificateServiceImpl certificateService;
    @Mock
    GiftCertificateRepositoryImpl certificateRepository;
    @Mock
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllCertificates(@Mock GiftCertificateParams paramsMock) {
        List<GiftCertificate> expCerts = getMockCertificates();
        Map<Long, List<Tag>> expTags = getMockCertificatesTags();
        expCerts.forEach(c -> c.setTags(expTags.get(c.getId())));

        List<GiftCertificate> certs = getMockCertificates();
        Map<Long, List<Tag>> tags = getMockCertificatesTags();
        certs.forEach(c -> c.setTags(tags.get(c.getId())));
        when(certificateRepository.getGiftCertificates(paramsMock, 1, 20)).thenReturn(certs);
        List<GiftCertificate> actualCerts = certificateService.getGiftCertificates(paramsMock, 1, 20);

        assertEquals(expCerts, actualCerts);
        verify(certificateRepository, only()).getGiftCertificates(paramsMock, 1, 20);
    }

    @Test
    public void getCertificateById() {
        GiftCertificate expCert = new GiftCertificate().setId(2L).setTitle("Spa");
        List<Tag> tags = getMockTags();
        expCert.setTags(tags);
        GiftCertificate cert = new GiftCertificate().setId(2L).setTitle("Spa");

        when(tagRepository.getCertificateTags(anyLong())).thenReturn(tags);
        when(certificateRepository.findById(anyLong())).thenReturn(cert);
        GiftCertificate actualCert = certificateService.findById(anyLong());

        assertEquals(expCert, actualCert);
        verify(tagRepository, only()).getCertificateTags(anyLong());
        verify(certificateRepository, only()).findById(anyLong());
    }

    @Test
    public void createNewCertificateWithoutTags(@Mock GiftCertificate certMock) {
        GiftCertificate expCert = new GiftCertificate().setId(2L).setTitle("Spa");
        List<Tag> expTags = getMockTagsWithoutId();
        expCert.setTags(expTags);

        when(certificateRepository.create(certMock)).thenReturn(expCert);
        GiftCertificate actualCert = certificateService.create(certMock);

        assertEquals(expCert, actualCert);
        verify(certificateRepository).create(certMock);
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithTags(@Mock GiftCertificate certMock) {
        GiftCertificate expCert = getMockCertificate();
        List<Tag> expTags = getMockTags();
        expCert.setTags(expTags);

        GiftCertificate cert = getMockCertificate();
        List<Tag> tags = getMockTagsWithoutId();
        cert.setTags(tags);

        when(tagRepository.createNonExistent(anyList())).thenReturn(expTags);
        when(tagRepository.getCertificateTags(anyLong())).thenReturn(expTags);
        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        GiftCertificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(tagRepository).createNonExistent(anyList());
        verify(tagRepository).getCertificateTags(anyLong());
        verify(certificateRepository).update(1L, certMock);
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithEmptyTags(@Mock GiftCertificate certMock) {
        GiftCertificate expCert = getMockCertificate();
        List<Tag> expTags = getMockTags();
        expCert.setTags(expTags);

        GiftCertificate cert = getMockCertificate();
        List<Tag> tags = new ArrayList<>();
        cert.setTags(tags);

        when(tagRepository.getCertificateTags(anyLong())).thenReturn(expTags);
        when(certificateRepository.update(1L, certMock)).thenReturn(cert);
        GiftCertificate actualCert = certificateService.update(1L, certMock);

        assertEquals(expCert, actualCert);

        verify(tagRepository).getCertificateTags(anyLong());
        verify(certificateRepository).update(1L, certMock);
        verifyNoMoreInteractions(certificateRepository);
    }

    @Test
    public void updateCertificateWithNullTags(@Mock GiftCertificate certMock) {
        GiftCertificate expCert = getMockCertificate();
        List<Tag> expTags = getMockTags();
        expCert.setTags(expTags);

        GiftCertificate cert = getMockCertificate();
        List<Tag> tags = getMockTags();

        when(tagRepository.getCertificateTags(anyLong())).thenReturn(tags);
        when(certificateRepository.update(eq(1L), any(GiftCertificate.class))).thenReturn(cert);
        GiftCertificate actualCert = certificateService.update(1L, cert);

        assertEquals(expCert, actualCert);

        verify(tagRepository, only()).getCertificateTags(anyLong());
        verify(certificateRepository, only()).update(eq(1L), any(GiftCertificate.class));
    }

    @Test
    public void deleteCertificate() {
        certificateService.delete(anyLong());
        verify(certificateRepository, only()).delete(anyLong());
    }

    private List<GiftCertificate> getMockCertificates() {
        return List.of(
                new GiftCertificate().setId(1L).setTitle("Trip around the world"),
                new GiftCertificate().setId(2L).setTitle("Spa"),
                new GiftCertificate().setId(5L).setTitle("Sailing")
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

    private GiftCertificate getMockCertificate() {
        return new GiftCertificate()
                .setId(1L)
                .setTitle("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100);
    }
}
