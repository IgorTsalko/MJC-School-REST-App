package com.epam.esm.repository;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;

import java.util.List;

public interface CertificateRepository {

    /**
     * Retrieve <code>Certificates</code> for appropriate parameters.
     * If there are no any parameters then return some <code>Certificates</code>
     * in an amount equal to the <code>limit</code> for page number <code>page</code>
     *
     * @param params the object that contains filtering or sorting parameters
     *               for retrieve <code>Certificates</code>
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of appropriate <code>Certificates</code>
     */
    List<Certificate> retrieveCertificates(CertificateSearchParams params, int page, int limit);

    /**
     * Retrieve certain <code>Certificate</code> for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain <code>Certificate</code>
     */
    Certificate findById(Long id);

    /**
     * Create new <code>Certificate</code> and return it
     *
     * @param certificate the object that contain properties for new <code>Certificate</code>
     * @return created <code>Certificate</code>
     */
    Certificate save(Certificate certificate);

    /**
     * Fully updates a specific <code>Certificate</code> or creates a new one if such not exists
     *
     * @param id specific certificate's identifier
     * @param certificate the object that contain properties for updating
     *                    or creating <code>Certificate</code>
     * @return updated or created <code>Certificate</code>
     */
    Certificate replace(Long id, Certificate certificate);

    /**
     * Update certain fields of a certain <code>Certificate</code> and return it
     *
     * @param id specific certificate's identifier
     * @param certificate the object that contain properties for updating <code>Certificate</code>
     * @return updated <code>Certificate</code>
     */
    Certificate update(Long id, Certificate certificate);

    /**
     * Delete <code>Certificate</code> by certain id
     *
     * @param id specific certificate's identifier
     */
    void delete(Long id);
}
