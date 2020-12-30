package com.epam.esm.service;

import com.epam.esm.object.Certificate;
import com.epam.esm.repository.GiftCertificatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificatesService {

    private GiftCertificatesRepository giftCertificatesRepository;

    @Autowired
    public GiftCertificatesService(GiftCertificatesRepository giftCertificatesRepository) {
        this.giftCertificatesRepository = giftCertificatesRepository;
    }

    public List<Certificate> allCertificates() {
        return giftCertificatesRepository.allCertificates();
    }
}
