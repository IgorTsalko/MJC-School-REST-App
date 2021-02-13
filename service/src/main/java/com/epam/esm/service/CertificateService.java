package com.epam.esm.service;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;

import java.util.List;

public interface CertificateService {

    /**
     * Retrieve list of {@link Certificate} for appropriate parameters.
     * If there are no any parameters then return some <code>Certificates</code>
     * in an amount equal to the <code>limit</code> for page number <code>page</code>
     *
     * @param params the object that contains filtering or sorting parameters
     *               for retrieve appropriate objects of {@link Certificate}
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of appropriate {@link Certificate}
     */
    List<Certificate> getCertificates(CertificateSearchParams params, int page, int limit);

    /**
     * Retrieve certain {@link Certificate} for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain {@link Certificate}
     */
    Certificate findById(Long id);

    /**
     * Create new {@link Certificate} and return it
     *
     * @param certificate the object that contain properties for new {@link Certificate}
     * @return created {@link Certificate}
     */
    Certificate create(Certificate certificate);

    /**
     * Fully updates a specific {@link Certificate} or creates a new one if such not exists
     *
     * @param id specific certificate's identifier
     * @param certificate the object that contain properties for updating
     *                    or creating {@link Certificate}
     * @return updated or created {@link Certificate}
     */
    Certificate replace(Long id, Certificate certificate);

    /**
     * Update certain fields of a certain {@link Certificate} and return it
     *
     * @param id specific certificate's identifier
     * @param certificate the object that contain properties for updating {@link Certificate}
     * @return updated {@link Certificate}
     */
    Certificate update(Long id, Certificate certificate);

    /**
     * Delete {@link Certificate} by certain id
     *
     * @param id specific certificate's identifier
     */
    void delete(Long id);
}
