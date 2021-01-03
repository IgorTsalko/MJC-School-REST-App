package com.epam.esm.server;

import com.epam.esm.object.CertificateDTO;
import com.epam.esm.service.CertificatesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("certificates")
public class CertificatesController {

    private CertificatesService certificatesService;

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
    public List<CertificateDTO> createNewCertificate(@RequestBody CertificateDTO certificate) {
        certificatesService.createNewCertificate(certificate);
        return certificatesService.allCertificates();
    }

    @PutMapping(value = "/{id}")
    public List<CertificateDTO> updateCertificateById(@PathVariable int id, @RequestBody CertificateDTO certificate) {
        certificatesService.updateCertificateById(id, certificate);
        return certificatesService.allCertificates();
    }

    @DeleteMapping(value = "/{id}")
    public List<CertificateDTO> deleteCertificateById(@PathVariable int id) {
        certificatesService.deleteCertificateById(id);
        return certificatesService.allCertificates();
    }
}
