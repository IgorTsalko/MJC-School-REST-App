package com.epam.esm.server.controller;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.server.entity.*;
import com.epam.esm.server.mapper.GiftCertificateMapper;
import com.epam.esm.server.mapper.GiftCertificateParamsMapper;
import com.epam.esm.server.security.AdministratorAllowed;
import com.epam.esm.service.GiftCertificateService;
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
            @Valid GiftCertificateParamsRequest paramsRequest,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        GiftCertificateParams params = GiftCertificateParamsMapper.convertToEntity(paramsRequest);

        List<GiftCertificateResponse> certificates = giftCertificateService.getGiftCertificates(params, pageNumber, limit)
                .stream().map(GiftCertificateMapper::convertToResponse)
                .collect(Collectors.toList());
        certificates.forEach(c -> {
            c.getTags().forEach(t -> t.add(linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel()));
            c.add(linkTo(methodOn(GiftCertificateController.class).findById(c.getId())).withSelfRel());
        });

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificates(null, page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificates(paramsRequest, 1, limit)).withRel("first"));

        if (page > 0 && certificates.size() == limit) {
            links.add(linkTo(methodOn(GiftCertificateController.class)
                    .getGiftCertificates(paramsRequest, page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(GiftCertificateController.class)
                    .getGiftCertificates(paramsRequest, page - 1, limit)).withRel("previous"));
        }

        return CollectionModel.of(certificates, links);
    }

    /**
     * Find {@link GiftCertificate} by <code>id</code>
     *
     * @param id specific certificate's identifier
     * @return certain {@link GiftCertificate} represented as {@link GiftCertificateResponse}
     */
    @GetMapping("/{id}")
    public GiftCertificateResponse findById(@PathVariable @Positive Long id) {
        GiftCertificateResponse giftCertificateResponse = GiftCertificateMapper.convertToResponse(giftCertificateService.findById(id));
        giftCertificateResponse.getTags()
                .forEach(t -> t.add(linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel()));
        giftCertificateResponse.add(linkTo(methodOn(GiftCertificateController.class).findById(id)).withSelfRel());
        return giftCertificateResponse;
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
        List<TagResponse> tags = giftCertificateResponse.getTags();
        if (tags != null) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel()));
        }
        giftCertificateResponse.add(linkTo(methodOn(GiftCertificateController.class).create(request)).withSelfRel());
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
        List<TagResponse> tags = giftCertificateResponse.getTags();
        if (tags != null) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel()));
        }
        giftCertificateResponse.add(linkTo(methodOn(GiftCertificateController.class).replace(id, request)).withSelfRel());
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
        List<TagResponse> tags = giftCertificateResponse.getTags();
        if (tags != null) {
            tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel()));
        }
        giftCertificateResponse.add(linkTo(methodOn(GiftCertificateController.class).update(id, request)).withSelfRel());
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
