package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.CertificateParamsDTO;
import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    public void getAllCertificates(@Mock CertificateParamsDTO params) {
        List<CertificateDTO> certs = List.of(
                new CertificateDTO().setId(1).setName("Trip around the world"),
                new CertificateDTO().setId(2).setName("Spa"),
                new CertificateDTO().setId(5).setName("Sailing")
        );
        List<CertificateDTO> expCerts = List.of(
                new CertificateDTO().setId(1).setName("Trip around the world"),
                new CertificateDTO().setId(2).setName("Spa"),
                new CertificateDTO().setId(5).setName("Sailing")
        );
        Map<Integer, List<TagDTO>> certTags = Map.of(
                1, List.of(new TagDTO().setId(1).setName("incredible"), new TagDTO().setId(3).setName("pleasure")),
                5, List.of(new TagDTO().setId(1).setName("incredible"), new TagDTO().setId(4).setName("jump"))
        );
        expCerts.forEach(c -> c.setTags(certTags.get(c.getId())));

        when(certificateRepository.getCertificates(params)).thenReturn(certs);
        when(tagRepository.getCertificateTags(anyList())).thenReturn(certTags);
        List<CertificateDTO> realCerts = certificateService.getCertificates(params);

        assertEquals(expCerts, realCerts);
        verify(certificateRepository, only()).getCertificates(params);
        verify(tagRepository, only()).getCertificateTags(anyList());
    }

    @Test
    public void get_certificate_by_id() {
        CertificateDTO cert = new CertificateDTO().setId(2).setName("Spa");
        CertificateDTO expCert = new CertificateDTO().setId(2).setName("Spa");
        List<TagDTO> tags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(3).setName("pleasure")
        );
        expCert.setTags(tags);

        when(tagRepository.getCertificateTags(anyInt())).thenReturn(tags);
        when(certificateRepository.getCertificate(anyInt())).thenReturn(cert);
        CertificateDTO realCert = certificateService.getCertificate(anyInt());

        assertEquals(expCert, realCert);
        verify(tagRepository, only()).getCertificateTags(anyInt());
        verify(certificateRepository, only()).getCertificate(anyInt());
    }

    @Test
    public void delete_certificate() {
        certificateService.deleteCertificate(anyInt());
        verify(certificateRepository, only()).deleteCertificate(anyInt());
    }
}
