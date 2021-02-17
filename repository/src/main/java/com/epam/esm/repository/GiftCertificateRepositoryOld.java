package com.epam.esm.repository;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;

import java.util.List;

public interface GiftCertificateRepositoryOld {

    /**
     * Retrieve list of {@link GiftCertificate} for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     * If there are no any parameters then return some list of {@link GiftCertificate}
     *
     * @param params the object that contains filtering or sorting parameters
     *               for retrieve list of {@link GiftCertificate}
     * @param page   number of page
     * @param limit  number of entities in the response
     * @return list of appropriate {@link GiftCertificate}
     */
    List<GiftCertificate> getGiftCertificates(GiftCertificateParams params, int page, int limit);
}
