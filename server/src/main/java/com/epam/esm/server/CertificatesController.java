package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.service.CertificatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public List<CertificateResponse> retrieveAllCertificates() {
        return certificatesService.getAllCertificates()
                .stream().map(CertificateMapper::dtoToResponse).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public CertificateResponse retrieveCertificateById(@PathVariable int id) {
        return CertificateMapper.dtoToResponse(certificatesService.getCertificate(id));
    }

    @PostMapping
    public ResponseEntity<CertificateResponse> createNewCertificate(@RequestBody CertificateRequest request) {
        CertificateDTO certificateDTO = certificatesService.createNewCertificate(CertificateMapper.requestToDto(request));
        return ResponseEntity.ok(CertificateMapper.dtoToResponse(certificateDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CertificateResponse> updateCertificateById(
            @PathVariable int id, @RequestBody CertificateRequest request) {
        request.setId(id);
        CertificateDTO certificateDTO = certificatesService.updateCertificate(CertificateMapper.requestToDto(request));
        return ResponseEntity.ok(CertificateMapper.dtoToResponse(certificateDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCertificateById(@PathVariable int id) {
        certificatesService.deleteCertificateById(id);
        return ResponseEntity.noContent().build();
    }
}
