package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<CertificateResponse> retrieveAllCertificates() {
        return certificateService.getAllCertificates()
                .stream().map(CertificateMapper::dtoToResponse).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public CertificateResponse retrieveCertificate(@PathVariable int id) {
        return CertificateMapper.dtoToResponse(certificateService.getCertificate(id));
    }

    @PostMapping
    public ResponseEntity<CertificateResponse> createNewCertificate(@RequestBody CertificateRequest request) {
        CertificateDTO certificateDTO = certificateService.createNewCertificate(CertificateMapper.requestToDto(request));
        return ResponseEntity.ok(CertificateMapper.dtoToResponse(certificateDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CertificateResponse> updateCertificate(
            @PathVariable int id, @RequestBody CertificateRequest request) {
        CertificateDTO certificateDTO = certificateService.updateCertificate(id, CertificateMapper.requestToDto(request));
        return ResponseEntity.ok(CertificateMapper.dtoToResponse(certificateDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCertificate(@PathVariable int id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
