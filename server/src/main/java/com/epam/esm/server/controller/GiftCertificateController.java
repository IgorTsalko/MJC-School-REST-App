package com.epam.esm.server.controller;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.filtering.GiftCertificateFilteringParams;
import com.epam.esm.server.entity.*;
import com.epam.esm.server.mapper.GiftCertificateMapper;
import com.epam.esm.server.mapper.GiftCertificateParamsMapper;
import com.epam.esm.server.security.AdministratorAllowed;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/gift_certificates")
@Validated
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Retrieve list of {@link GiftCertificate} for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     * If there are no any parameters then return some list of {@link GiftCertificate}
     * represented as list of {@link GiftCertificateResponse}
     *
     * @param paramsRequest the object that contains filtering or sorting parameters
     *               for retrieve list of {@link GiftCertificate}
     * @param page   number of page
     * @param limit  number of entities in the response
     * @return list of appropriate {@link GiftCertificate}
     * represented as list of {@link GiftCertificateResponse}
     */
    @GetMapping
    public CollectionModel<GiftCertificateResponse> getGiftCertificates(
            @Valid GiftCertificateFilteringParamsRequest paramsRequest,
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        GiftCertificateFilteringParams params = GiftCertificateParamsMapper.convertToEntity(paramsRequest);

        List<GiftCertificateResponse> giftCertificateResponses = giftCertificateService
                .getGiftCertificates(params, page, limit)
                .stream()
                .map(GiftCertificateMapper::convertToResponse)
                .collect(Collectors.toList());

        giftCertificateResponses.forEach(cert -> {
            assignTagSelfLink(cert.getTags());
            assignGiftCertificateSelfLink(cert);
        });

        return CollectionModel.of(
                giftCertificateResponses,
                generateGiftCertificatesLinks(giftCertificateResponses.size(), page, limit)
        );
    }

    private void assignGiftCertificateSelfLink(GiftCertificateResponse giftCertificateResponse) {
        giftCertificateResponse.add(
                linkTo(methodOn(GiftCertificateController.class).findById(giftCertificateResponse.getId())).withSelfRel()
        );
    }

    private List<Link> generateGiftCertificatesLinks(int resultSize, int page, int limit) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificates(null, page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificates(null, 1, limit)).withRel("first"));

        if (resultSize == limit) {
            links.add(linkTo(methodOn(GiftCertificateController.class)
                    .getGiftCertificates(null, page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(GiftCertificateController.class)
                    .getGiftCertificates(null, page - 1, limit)).withRel("previous"));
        }

        return links;
    }

    /**
     * Find {@link GiftCertificate} by <code>id</code>
     *
     * @param id specific certificate's identifier
     * @return certain {@link GiftCertificate} represented as {@link GiftCertificateResponse}
     */
    @GetMapping("/{id}")
    public GiftCertificateResponse findById(@PathVariable @Positive Long id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        GiftCertificateResponse giftCertificateResponse = GiftCertificateMapper.convertToResponse(giftCertificate);

        assignTagSelfLink(giftCertificateResponse.getTags());
        assignGiftCertificateSelfLink(giftCertificateResponse);

        return giftCertificateResponse;
    }

    private void assignTagSelfLink(List<TagResponse> tags) {
        if (!CollectionUtils.isEmpty(tags)) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel()));
        }
    }

    /**
     * Persist new {@link GiftCertificate} and return it represented as {@link GiftCertificateResponse}
     *
     * @param request the object that contain properties for new {@link GiftCertificate}
     * @return created {@link GiftCertificate} represented as {@link GiftCertificateResponse}
     */
    @AdministratorAllowed
    @PostMapping
    public ResponseEntity<GiftCertificateResponse> create(@RequestBody @Valid GiftCertificateCreateRequest request) {
        GiftCertificate giftCertificate = giftCertificateService.create(GiftCertificateMapper.convertToEntity(request));
        GiftCertificateResponse giftCertificateResponse = GiftCertificateMapper.convertToResponse(giftCertificate);

        assignTagSelfLink(giftCertificateResponse.getTags());
        assignGiftCertificateSelfLink(giftCertificateResponse);

        return new ResponseEntity<>(giftCertificateResponse, HttpStatus.CREATED);
    }

    /**
     * Fully updates a specific {@link GiftCertificate}
     *
     * @param id      specific certificate's identifier
     * @param request the object that contain properties for updating or creating {@link GiftCertificate}
     * @return updated {@link GiftCertificate} represented as {@link GiftCertificateResponse}
     */
    @AdministratorAllowed
    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateResponse> replace(
            @PathVariable @Positive Long id, @RequestBody @Valid GiftCertificateCreateRequest request) {
        GiftCertificate giftCertificate = giftCertificateService.replace(id, GiftCertificateMapper.convertToEntity(request));
        GiftCertificateResponse giftCertificateResponse = GiftCertificateMapper.convertToResponse(giftCertificate);

        assignTagSelfLink(giftCertificateResponse.getTags());
        assignGiftCertificateSelfLink(giftCertificateResponse);

        return ResponseEntity.ok(giftCertificateResponse);
    }

    /**
     * Update certain fields of a certain {@link GiftCertificate} and
     * return updated {@link GiftCertificate} represented as {@link GiftCertificateResponse}
     *
     * @param id      specific certificate's identifier
     * @param request the object that contain properties for updating {@link GiftCertificate}
     * @return updated {@link GiftCertificate} represented as {@link GiftCertificateResponse}
     */
    @AdministratorAllowed
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateResponse> update(
            @PathVariable @Positive Long id, @RequestBody @Valid GiftCertificateUpdateRequest request) {
        GiftCertificate giftCertificate = giftCertificateService.update(id, GiftCertificateMapper.convertToEntity(request));
        GiftCertificateResponse giftCertificateResponse = GiftCertificateMapper.convertToResponse(giftCertificate);

        assignTagSelfLink(giftCertificateResponse.getTags());
        assignGiftCertificateSelfLink(giftCertificateResponse);

        return ResponseEntity.ok(giftCertificateResponse);
    }

    /**
     * Delete {@link GiftCertificate} by certain id
     *
     * @param id specific certificate's identifier
     * @return successful status code <code>NO_CONTENT 204</code>
     */
    @AdministratorAllowed
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
