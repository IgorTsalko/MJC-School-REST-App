package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.server.entity.*;
import com.epam.esm.server.mapper.CertificateMapper;
import com.epam.esm.server.mapper.SearchParamsMapper;
import com.epam.esm.service.CertificateService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
@Validated
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Retrieve <code>Certificates</code> for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     * If there are no any parameters then return some <code>Certificates</code>
     *
     * @param params the object that contains filtering or sorting parameters
     *               for retrieve <code>Certificates</code>
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of appropriate <code>Certificates</code>
     */
    @GetMapping
    public CollectionModel<CertificateResponse> getCertificates(
            @Valid CertificateSearchParamsRequest params,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        CertificateSearchParams searchParams = SearchParamsMapper.convertToEntity(params);

        List<CertificateResponse> certificates = certificateService.getCertificates(searchParams, pageNumber, limit)
                .stream().map(CertificateMapper::convertToResponse)
                .collect(Collectors.toList());
        certificates.forEach(c -> {
            c.getTags().forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));
            c.add(linkTo(methodOn(CertificateController.class).get(c.getId())).withSelfRel());
        });

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(CertificateController.class)
                .getCertificates(null, page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(CertificateController.class)
                .getCertificates(params, 1, limit)).withRel("first"));

        if (page > 0 && certificates.size() == limit) {
            links.add(linkTo(methodOn(CertificateController.class)
                    .getCertificates(params,page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(CertificateController.class)
                    .getCertificates(params, page - 1, limit)).withRel("previous"));
        }

        return CollectionModel.of(certificates, links);
    }

    /**
     * Retrieve certain <code>Certificate</code> for appropriate id.
     *
     * @param id specific certificate's identifier
     * @return certain <code>Certificate</code>
     */
    @GetMapping("/{id}")
    public CertificateResponse get(@PathVariable @Positive Long id) {
        CertificateResponse certificateResponse = CertificateMapper.convertToResponse(certificateService.get(id));
        certificateResponse.getTags()
                .forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));
        certificateResponse.add(linkTo(methodOn(CertificateController.class).get(id)).withSelfRel());
        return certificateResponse;
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
        CertificateResponse certificateResponse = CertificateMapper.convertToResponse(certificate);
        List<TagResponse> tags = certificateResponse.getTags();
        if (tags != null) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));
        }
        certificateResponse.add(linkTo(methodOn(CertificateController.class).create(request)).withSelfRel());
        return new ResponseEntity<>(certificateResponse, HttpStatus.CREATED);
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
        CertificateResponse certificateResponse = CertificateMapper.convertToResponse(certificate);
        List<TagResponse> tags = certificateResponse.getTags();
        if (tags != null) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));
        }
        certificateResponse.add(linkTo(methodOn(CertificateController.class).put(id, request)).withSelfRel());
        return ResponseEntity.ok(certificateResponse);
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
        CertificateResponse certificateResponse = CertificateMapper.convertToResponse(certificate);
        List<TagResponse> tags = certificateResponse.getTags();
        if (tags != null) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));
        }
        certificateResponse.add(linkTo(methodOn(CertificateController.class).partialUpdate(id, request)).withSelfRel());
        return ResponseEntity.ok(certificateResponse);
    }

    /**
     * Delete <code>Certificate</code> by certain id
     *
     * @param id specific certificate's identifier
     * @return successful status code <code>NO_CONTENT 204</code>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        certificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
