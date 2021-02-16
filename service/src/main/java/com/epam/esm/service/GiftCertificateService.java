package com.epam.esm.service;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;

import java.util.List;

public interface GiftCertificateService {

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

    /**
     * Find {@link GiftCertificate} by <code>id</code>
     *
     * @param id specific certificate's identifier
     * @return certain {@link GiftCertificate}
     */
    GiftCertificate findById(Long id);

    /**
     * Persist new {@link GiftCertificate} and return it
     *
     * @param giftCertificate the object that contain properties for new {@link GiftCertificate}
     * @return created {@link GiftCertificate}
     */
    GiftCertificate create(GiftCertificate giftCertificate);

    /**
     * Fully updates a specific {@link GiftCertificate}
     *
     * @param id      specific certificate's identifier
     * @param giftCertificate the object that contain properties for updating
     *                    or creating {@link GiftCertificate}
     * @return updated {@link GiftCertificate}
     */
    GiftCertificate replace(Long id, GiftCertificate giftCertificate);

    /**
     * Update certain fields of a certain {@link GiftCertificate} and
     * return updated {@link GiftCertificate}
     *
     * @param id      specific certificate's identifier
     * @param giftCertificate the object that contain properties for updating {@link GiftCertificate}
     * @return updated {@link GiftCertificate}
     */
    GiftCertificate update(Long id, GiftCertificate giftCertificate);

    /**
     * Delete {@link GiftCertificate} by certain id
     *
     * @param id specific certificate's identifier
     */
    void delete(Long id);
}
