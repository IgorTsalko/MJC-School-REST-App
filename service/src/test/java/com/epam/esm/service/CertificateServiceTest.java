package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
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
    CertificateService certificateService;
    @Mock
    CertificateRepository certificateRepository;
    @Mock
    TagRepository tagRepository;

    static LocalDateTime createTime = LocalDateTime.of(2020, 12, 20, 16, 40);
    static LocalDateTime updatedTime = LocalDateTime.of(2021, 1, 14, 18, 27);

    @Test
    public void getAllCertificates(@Mock SearchParams paramsMock) {
        List<CertificateDTO> expCerts = List.of(
                new CertificateDTO().setId(1).setName("Trip around the world"),
                new CertificateDTO().setId(2).setName("Spa"),
                new CertificateDTO().setId(5).setName("Sailing")
        );
        Map<Integer, List<TagDTO>> expTags = Map.of(
                1, List.of(new TagDTO().setId(1).setName("incredible"), new TagDTO().setId(3).setName("pleasure")),
                5, List.of(new TagDTO().setId(1).setName("incredible"), new TagDTO().setId(4).setName("jump"))
        );
        expCerts.forEach(c -> c.setTags(expTags.get(c.getId())));

        List<CertificateDTO> certs = List.of(
                new CertificateDTO().setId(1).setName("Trip around the world"),
                new CertificateDTO().setId(2).setName("Spa"),
                new CertificateDTO().setId(5).setName("Sailing")
        );

        when(certificateRepository.getCertificates(paramsMock)).thenReturn(certs);
        when(tagRepository.getCertificateTags(anyList())).thenReturn(expTags);
        List<CertificateDTO> actualCerts = certificateService.getCertificates(paramsMock);

        assertEquals(expCerts, actualCerts);
        verify(certificateRepository, only()).getCertificates(paramsMock);
        verify(tagRepository, only()).getCertificateTags(anyList());
    }

    @Test
    public void getCertificateById() {
        CertificateDTO expCert = new CertificateDTO().setId(2).setName("Spa");
        List<TagDTO> tags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(3).setName("pleasure")
        );
        expCert.setTags(tags);
        CertificateDTO cert = new CertificateDTO().setId(2).setName("Spa");

        when(tagRepository.getCertificateTags(anyInt())).thenReturn(tags);
        when(certificateRepository.getCertificate(anyInt())).thenReturn(cert);
        CertificateDTO actualCert = certificateService.getCertificate(anyInt());

        assertEquals(expCert, actualCert);
        verify(tagRepository, only()).getCertificateTags(anyInt());
        verify(certificateRepository, only()).getCertificate(anyInt());
    }

    @Test
    public void createNewCertificate(@Mock CertificateDTO certMock) {
        CertificateDTO expCert = new CertificateDTO().setId(2).setName("Spa");
        List<TagDTO> tags = List.of(
                new TagDTO().setName("incredible"),
                new TagDTO().setName("pleasure")
        );
        expCert.setTags(tags);

        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("pleasure")
        );
        when(certificateRepository.createNewCertificate(certMock)).thenReturn(expCert);
        when(tagRepository.getTagsByName(anyList())).thenReturn(expTags);
        CertificateDTO actualCert = certificateService.createNewCertificate(certMock);

        assertEquals(expCert, actualCert);
        verify(certificateRepository).createNewCertificate(certMock);
        verify(tagRepository).createTagsIfNonExist(anyList());
        verify(tagRepository).getTagsByName(anyList());
        verify(certificateRepository).addCertificateTagConnections(anyInt(), anyList());
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithTags(@Mock CertificateDTO certMock) {
        CertificateDTO expCert = new CertificateDTO()
                .setId(1)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(createTime)
                .setLastUpdateDate(updatedTime);
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("pleasure")
        );
        expCert.setTags(expTags);

        CertificateDTO cert = new CertificateDTO()
                .setId(1)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(createTime)
                .setLastUpdateDate(updatedTime);
        List<TagDTO> tags = List.of(
                new TagDTO().setName("incredible"),
                new TagDTO().setName("pleasure")
        );
        cert.setTags(tags);

        when(certificateRepository.updateCertificate(1, certMock)).thenReturn(cert);
        when(tagRepository.getTagsByName(anyList())).thenReturn(expTags);
        CertificateDTO actualCert = certificateService.updateCertificate(1, certMock);

        assertEquals(expCert, actualCert);

        verify(certificateRepository).updateCertificate(1, certMock);
        verify(certificateRepository).deleteCertificateTagConnections(anyInt());
        verify(tagRepository).createTagsIfNonExist(anyList());
        verify(tagRepository).getTagsByName(anyList());
        verify(certificateRepository).addCertificateTagConnections(anyInt(), anyList());
        verifyNoMoreInteractions(certificateRepository, tagRepository);
    }

    @Test
    public void updateCertificateWithEmptyTags(@Mock CertificateDTO certMock) {
        CertificateDTO expCert = new CertificateDTO()
                .setId(1)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(createTime)
                .setLastUpdateDate(updatedTime);

        CertificateDTO cert = new CertificateDTO()
                .setId(1)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(createTime)
                .setLastUpdateDate(updatedTime);
        List<TagDTO> tags = new ArrayList<>();
        cert.setTags(tags);

        when(certificateRepository.updateCertificate(1, certMock)).thenReturn(cert);
        CertificateDTO actualCert = certificateService.updateCertificate(1, certMock);

        assertEquals(expCert, actualCert);

        verify(certificateRepository).updateCertificate(1, certMock);
        verify(certificateRepository).deleteCertificateTagConnections(anyInt());
        verifyNoMoreInteractions(certificateRepository);
    }

    @Test
    public void updateCertificateWithNullTags(@Mock CertificateDTO certMock) {
        CertificateDTO expCert = new CertificateDTO()
                .setId(1)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(createTime)
                .setLastUpdateDate(updatedTime);
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("pleasure")
        );
        expCert.setTags(expTags);

        CertificateDTO cert = new CertificateDTO()
                .setId(1)
                .setName("newUpdatedName")
                .setPrice(BigDecimal.valueOf(120.0))
                .setDuration(100)
                .setCreateDate(createTime)
                .setLastUpdateDate(updatedTime);
        List<TagDTO> tags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("pleasure")
        );

        when(certificateRepository.updateCertificate(1, certMock)).thenReturn(cert);
        when(tagRepository.getCertificateTags(anyInt())).thenReturn(tags);
        CertificateDTO actualCert = certificateService.updateCertificate(1, certMock);

        assertEquals(expCert, actualCert);

        verify(certificateRepository, only()).updateCertificate(1, certMock);
        verify(tagRepository, only()).getCertificateTags(anyInt());
    }

    @Test
    public void deleteCertificate() {
        certificateService.deleteCertificate(anyInt());
        verify(certificateRepository, only()).deleteCertificate(anyInt());
    }
}
