package com.epam.esm.repository;

import com.epam.esm.object.Certificate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificatesRepository {

    private List<Certificate> certificates;

    {
        certificates = new ArrayList<>();
        Certificate gift = new Certificate();
        gift.setName("Computer");
        gift.setPrice(1000.0);
        gift.setCreateDate(LocalDate.now());
        certificates.add(gift);

        gift = new Certificate();
        gift.setName("Mobile Phone");
        gift.setPrice(300.0);
        gift.setCreateDate(LocalDate.now());
        certificates.add(gift);

        gift = new Certificate();
        gift.setName("Book");
        gift.setPrice(25.0);
        gift.setCreateDate(LocalDate.now());
        certificates.add(gift);
    }

    public List<Certificate> allCertificates() {
        return certificates;
    }
}
