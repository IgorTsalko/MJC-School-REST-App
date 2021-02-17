package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateSpecification;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GiftGiftCertificateServiceTest {

    @InjectMocks
    GiftCertificateServiceImpl certificateService;
    @Mock
    GiftCertificateRepository giftCertificateRepository;

    @Test
    public void getGiftCertificatesWithoutParams(@Mock Page<GiftCertificate> page) {
        GiftCertificateParams paramsMock = new GiftCertificateParams();
        Sort sorting = Sort.by(Sort.Order.asc("id"));
        List<GiftCertificate> expCerts = getMockCertificates();
        Map<Long, List<Tag>> expTags = getMockCertificatesTags();
        expCerts.forEach(c -> c.setTags(expTags.get(c.getId())));

        when(giftCertificateRepository
                .findAll(any(GiftCertificateSpecification.class), eq(PageRequest.of(0, 20, sorting))))
                .thenReturn(page);
        when(page.getContent()).thenReturn(expCerts);

        List<GiftCertificate> actualCerts = certificateService.getGiftCertificates(paramsMock, 1, 20);

        assertEquals(expCerts, actualCerts);
        verify(giftCertificateRepository, only())
                .findAll(any(GiftCertificateSpecification.class), eq(PageRequest.of(0, 20, sorting)));
    }

    @Test
    public void getCertificateById() {
        GiftCertificate expCert = new GiftCertificate().setId(2L).setTitle("Spa");
        List<Tag> tags = getMockTags();
        expCert.setTags(tags);

        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(expCert));
        GiftCertificate actualCert = certificateService.findById(anyLong());

        assertEquals(expCert, actualCert);
        verify(giftCertificateRepository, only()).findById(anyLong());
    }

    @Test
    public void createNewCertificateWithoutTags(@Mock GiftCertificate certMock) {
        GiftCertificate expCert = new GiftCertificate().setId(2L).setTitle("Spa");

        when(giftCertificateRepository.save(certMock)).thenReturn(expCert);
        GiftCertificate actualCert = certificateService.create(certMock);

        assertEquals(expCert, actualCert);
        verify(giftCertificateRepository, only()).save(certMock);
    }

    @Test
    public void deleteCertificate() {
        when(giftCertificateRepository.existsById(anyLong())).thenReturn(true);

        certificateService.delete(anyLong());
        verify(giftCertificateRepository).existsById(anyLong());
        verify(giftCertificateRepository).deleteById(anyLong());
        verifyNoMoreInteractions(giftCertificateRepository);
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
}
