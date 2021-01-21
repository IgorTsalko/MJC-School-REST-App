package com.epam.esm.repository;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.Tag;

import java.util.List;

public interface CertificateRepository {

    /**
     * Retrieve <code>Certificates</code> for appropriate parameters.
     * If there are no parameters then return all <code>Certificates</code>
     *
     * @param params the object that contains parameters for retrieve <code>Certificates</code>
     * @return list of appropriate <code>Certificates</code>
     */
    List<Certificate> getCertificates(SearchParams params);

    /**
     * Retrieve certain <code>Certificate</code> for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain <code>Certificate</code>
     */
    Certificate getCertificate(Long id);

    /**
     * Create new <code>Certificate</code> and return it
     *
     * @param certificate the object that contain properties for new <code>Certificate</code>
     * @return created <code>Certificate</code>
     */
    Certificate createNewCertificate(Certificate certificate);

    /**
     * Update certain <code>Certificate</code> and return it
     *
     * @param certificateId specific certificate's identifier
     * @param certificate the object that contain properties for updating <code>Certificate</code>
     * @return updated <code>Certificate</code>
     */
    Certificate updateCertificate(Long certificateId, Certificate certificate);

    /**
     * Delete certain <code>Certificate</code>
     *
     * @param id specific certificate's identifier
     */
    void deleteCertificate(Long id);

    /**
     * Link certificates to matching tags
     *
     * @param certificateId specific certificate's identifier
     * @param tags list of <code>Tag</code> entities
     */
    void addCertificateTagConnections(Long certificateId, List<Tag> tags);

    /**
     * Delete certificate connections with matching tags
     *
     * @param certificateId specific certificate's identifier
     */
    void deleteCertificateTagConnections(Long certificateId);
}
