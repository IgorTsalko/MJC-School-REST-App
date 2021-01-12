package com.epam.esm.server;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.CertificateParamsDTO;
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

    @GetMapping
    public List<CertificateResponse> getCertificates(@Valid CertificateParamsDTO params) {
        return certificateService.getCertificates(params)
                .stream().map(CertificateMapper::dtoToResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CertificateResponse getCertificate(@PathVariable @Positive int id) {
        return CertificateMapper.dtoToResponse(certificateService.getCertificate(id));
    }

    @PostMapping
    public ResponseEntity<CertificateResponse> createNewCertificate(@RequestBody @Valid CertificateRequest request) {
        CertificateDTO certificateDTO = certificateService.createNewCertificate(CertificateMapper.requestToDto(request));
        return ResponseEntity.ok(CertificateMapper.dtoToResponse(certificateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateResponse> updateCertificate(
            @PathVariable @Positive int id, @RequestBody @Valid CertificateRequest request) {
        CertificateDTO certificateDTO = certificateService.updateCertificate(id, CertificateMapper.requestToDto(request));
        return ResponseEntity.ok(CertificateMapper.dtoToResponse(certificateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertificate(@PathVariable @Positive int id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
