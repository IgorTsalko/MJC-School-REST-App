package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.service.CertificatesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public List<CertificateDTO> retrieveAllCertificates() {
        return certificatesService.allCertificates();
    }

    @GetMapping(value = "/{id}")
    public CertificateDTO retrieveCertificateById(@PathVariable int id) {
        return certificatesService.getCertificate(id);
    }

    @PostMapping
    public ResponseEntity<ErrorDefinition> createNewCertificate(@RequestBody CertificateDTO certificate) {
        if (certificatesService.createNewCertificate(certificate)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ErrorDefinition.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCertificateById(
            @PathVariable int id, @RequestBody CertificateDTO certificate) {
        certificatesService.updateCertificateById(id, certificate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ErrorDefinition> deleteCertificateById(@PathVariable int id) {
        certificatesService.deleteCertificateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
