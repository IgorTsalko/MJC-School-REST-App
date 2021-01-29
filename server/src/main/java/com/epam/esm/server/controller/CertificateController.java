package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.server.entity.CertificateSearchParamsRequest;
import com.epam.esm.server.mapper.CertificateMapper;
import com.epam.esm.server.entity.CertificateCreateRequest;
import com.epam.esm.server.entity.CertificateUpdateRequest;
import com.epam.esm.server.entity.CertificateResponse;
import com.epam.esm.server.mapper.SearchParamsMapper;
import com.epam.esm.service.CertificateService;
import org.springframework.http.HttpStatus;
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
    public List<CertificateResponse> getAll(@Valid CertificateSearchParamsRequest params) {
        return certificateService.getAll(SearchParamsMapper.convertToEntity(params))
                .stream().map(CertificateMapper::convertToResponse).collect(Collectors.toList());
    }

    /**
     * Retrieve certain <code>Certificate</code> for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain <code>Certificate</code>
     */
    @GetMapping("/{id}")
    public CertificateResponse get(@PathVariable @Positive Long id) {
        return CertificateMapper.convertToResponse(certificateService.get(id));
    }

    /**
     * Create new <code>Certificate</code> and return it
     *
     * @param request the object that contain properties for new <code>Certificate</code>
     * @return created <code>Certificate</code>
     */
    @PostMapping
    public ResponseEntity<CertificateResponse> create(@RequestBody @Valid CertificateCreateRequest request) {
        Certificate certificate = certificateService.create(CertificateMapper.convertToEntity(request));
        return new ResponseEntity<>(CertificateMapper.convertToResponse(certificate), HttpStatus.CREATED);
    }

    /**
     * Fully updates a specific <code>Certificate</code> or creates a new one
     * if such not exists and return it
     *
     * @param id specific certificate's identifier
     * @param request the object that contain properties for updating or creating <code>Certificate</code>
     * @return updated or created <code>Certificate</code>
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificateResponse> put(
            @PathVariable @Positive Long id, @RequestBody @Valid CertificateCreateRequest request) {
        Certificate certificate = certificateService.put(id, CertificateMapper.convertToEntity(request));
        return ResponseEntity.ok(CertificateMapper.convertToResponse(certificate));
    }

    /**
     * Update certain fields of a certain <code>Certificate</code> and return it
     *
     * @param id specific certificate's identifier
     * @param request the object that contain properties for updating <code>Certificate</code>
     * @return updated <code>Certificate</code>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CertificateResponse> partialUpdate(
            @PathVariable @Positive Long id, @RequestBody @Valid CertificateUpdateRequest request) {
        Certificate certificate = certificateService.update(id, CertificateMapper.convertToEntity(request));
        return ResponseEntity.ok(CertificateMapper.convertToResponse(certificate));
    }

    /**
     * Delete certain <code>Certificate</code>
     *
     * @param id specific certificate's identifier
     * @return successful status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        certificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
