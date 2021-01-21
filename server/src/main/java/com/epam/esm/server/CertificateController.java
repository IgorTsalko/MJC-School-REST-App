package com.epam.esm.server;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.server.entity.CertificateRequest;
import com.epam.esm.server.entity.CertificateResponse;
import com.epam.esm.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("certificates")
@Validated
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Retrieve <code>Certificates</code> for appropriate parameters.
     * If there are no parameters then return all <code>Certificates</code>
     *
     * @param params the object that contains parameters for retrieve <code>Certificates</code>
     * @return list of appropriate <code>Certificates</code>
     */
    @GetMapping
    public List<CertificateResponse> getCertificates(@Valid SearchParams params) {
        return certificateService.getCertificates(params)
                .stream().map(CertificateMapper::convertToResponse).collect(Collectors.toList());
    }

    /**
     * Retrieve certain <code>Certificate</code> for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain <code>Certificate</code>
     */
    @GetMapping("/{id}")
    public CertificateResponse getCertificate(@PathVariable @Positive Long id) {
        return CertificateMapper.convertToResponse(certificateService.getCertificate(id));
    }

    /**
     * Create new <code>Certificate</code> and return it
     *
     * @param request the object that contain properties for new <code>Certificate</code>
     * @return created <code>Certificate</code>
     */
    @PostMapping
    public ResponseEntity<CertificateResponse> createNewCertificate(@RequestBody @Valid CertificateRequest request) {
        Certificate certificate = certificateService.createNewCertificate(CertificateMapper.convertToEntity(request));
        return ResponseEntity.ok(CertificateMapper.convertToResponse(certificate));
    }

    /**
     * Update certain <code>Certificate</code> and return it
     *
     * @param id specific certificate's identifier
     * @param request the object that contain properties for updating <code>Certificate</code>
     * @return updated <code>Certificate</code>
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificateResponse> updateCertificate(
            @PathVariable @Positive Long id, @RequestBody @Valid CertificateRequest request) {
        Certificate certificate = certificateService.updateCertificate(id, CertificateMapper.convertToEntity(request));
        return ResponseEntity.ok(CertificateMapper.convertToResponse(certificate));
    }

    /**
     * Delete certain <code>Certificate</code>
     *
     * @param id specific certificate's identifier
     * @return successful status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertificate(@PathVariable @Positive Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
