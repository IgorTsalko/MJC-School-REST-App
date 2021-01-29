package com.epam.esm.service;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;

import java.util.List;

public interface CertificateService {

    /**
     * Retrieve <code>Certificates</code> for appropriate parameters.
     * If there are no parameters then return all <code>Certificates</code>
     *
     * @param params the object that contains parameters for retrieve <code>Certificates</code>
     * @return list of appropriate <code>Certificates</code>
     */
    List<Certificate> getAll(CertificateSearchParams params, Integer page, Integer limit);

    /**
     * Retrieve certain <code>Certificate</code> for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain <code>Certificate</code>
     */
    Certificate get(Long id);

    /**
     * Create new <code>Certificate</code> and return it
     *
     * @param certificate the object that contain properties for new <code>Certificate</code>
     * @return created <code>Certificate</code>
     */
    Certificate create(Certificate certificate);

    /**
     * Fully updates a specific <code>Certificate</code> or creates a new one if such not exists
     *
     * @param id specific certificate's identifier
     * @param certificate the object that contain properties for updating
     *                    or creating <code>Certificate</code>
     * @return updated or created <code>Certificate</code>
     */
    Certificate put(Long id, Certificate certificate);

    /**
     * Update certain fields of a certain <code>Certificate</code> and return it
     *
     * @param id specific certificate's identifier
     * @param certificate the object that contain properties for updating <code>Certificate</code>
     * @return updated <code>Certificate</code>
     */
    Certificate update(Long id, Certificate certificate);

    /**
     * Delete certain <code>Certificate</code>
     *
     * @param id specific certificate's identifier
     */
    void delete(Long id);
}
