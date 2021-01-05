package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.service.CertificatesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public List<CertificateResponse> retrieveAllCertificates() {
        List<CertificateResponse> certificateResponseList = new ArrayList<>();

        for (CertificateDTO certificateDTO: certificatesService.allCertificates()) {
            CertificateResponse certificateResponse = new CertificateResponse()
                    .setId(certificateDTO.getId())
                    .setName(certificateDTO.getName())
                    .setDescription(certificateDTO.getDescription())
                    .setPrice(certificateDTO.getPrice())
                    .setDuration(certificateDTO.getDuration())
                    .setCreateDate(certificateDTO.getCreateDate())
                    .setLastUpdateDate(certificateDTO.getLastUpdateDate());
            certificateResponseList.add(certificateResponse);
        }

        return certificateResponseList;
    }

    @GetMapping(value = "/{id}")
    public CertificateResponse retrieveCertificateById(@PathVariable int id) {
        CertificateDTO certificateDTO = certificatesService.getCertificate(id);
        return new CertificateResponse()
                .setId(certificateDTO.getId())
                .setName(certificateDTO.getName())
                .setDescription(certificateDTO.getDescription())
                .setPrice(certificateDTO.getPrice())
                .setDuration(certificateDTO.getDuration())
                .setCreateDate(certificateDTO.getCreateDate())
                .setLastUpdateDate(certificateDTO.getLastUpdateDate());

    }

    @PostMapping
    public ResponseEntity<Object> createNewCertificate(@RequestBody CertificateRequest certificate) {
        CertificateDTO certificateDTO = new CertificateDTO()
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration());
        certificatesService.createNewCertificate(certificateDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCertificateById(
            @PathVariable int id, @RequestBody CertificateDTO certificate) {
        certificate.setId(id);
        certificatesService.updateCertificateById(certificate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCertificateById(@PathVariable int id) {
        certificatesService.deleteCertificateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
