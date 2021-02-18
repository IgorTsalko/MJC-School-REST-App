package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.server.mapper.TagMapper;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;
import com.epam.esm.server.security.AdministratorAllowed;
import com.epam.esm.server.security.UserAllowed;
import com.epam.esm.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/v1/tags")
@Validated
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Retrieve list of {@link Tag} in an amount equal to the
     * <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Tag} represented as list of {@link TagResponse}
     */
    @UserAllowed
    @GetMapping
    public CollectionModel<TagResponse> getTags(
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        List<TagResponse> tags = tagService.getTags(page, limit)
                .stream()
                .map(TagMapper::convertToResponse)
                .collect(Collectors.toList());

        assignTagSelfLink(tags);

        return CollectionModel.of(tags, generateTagLinks(tags.size(), page, limit));
    }

    private void assignTagSelfLink(TagResponse tagResponse) {
        tagResponse.add(linkTo(methodOn(TagController.class).findById(tagResponse.getId())).withSelfRel());
    }

    private void assignTagSelfLink(List<TagResponse> tags) {
        tags.forEach(this::assignTagSelfLink);
    }

    private List<Link> generateTagLinks(int resultSize, int page, int limit) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TagController.class).getTags(page, limit)).withSelfRel());
        links.add(linkTo(methodOn(TagController.class).getTags(1, limit)).withRel("first"));

        if (resultSize == limit) {
            links.add(linkTo(methodOn(TagController.class).getTags(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(TagController.class).getTags(page - 1, limit)).withRel("previous"));
        }

        return links;
    }

    /**
     * Find {@link Tag} by <code>id</code> and return it represented as {@link TagResponse}
     *
     * @param id specific tag's identifier
     * @return certain {@link Tag} represented as {@link TagResponse}
     */
    @UserAllowed
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> findById(@PathVariable @Positive Long id) {
        TagResponse tagResponse = TagMapper.convertToResponse(tagService.findById(id));
        assignTagSelfLink(tagResponse);
        return ResponseEntity.ok(tagResponse);
    }

    /**
     * Persist new {@link Tag} and return it represented as {@link TagResponse}
     *
     * @param tagRequest the object that contain properties for new {@link Tag}
     * @return created {@link Tag} represented as {@link TagResponse}
     */
    @AdministratorAllowed
    @PostMapping
    public ResponseEntity<TagResponse> create(@RequestBody @Valid TagRequest tagRequest) {
        Tag tag = tagService.create(TagMapper.convertToEntity(tagRequest));
        TagResponse tagResponse = TagMapper.convertToResponse(tag);
        assignTagSelfLink(tagResponse);
        return ResponseEntity.ok(tagResponse);
    }

    /**
     * Delete {@link Tag} by <code>id</code> and return successful
     * status code <code>NO_CONTENT 204</code>
     *
     * @param id specific tag's identifier
     * @return successful status code <code>NO_CONTENT 204</code>
     */
    @AdministratorAllowed
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find the most widely used {@link Tag} of a user with the highest
     * cost of all orders
     *
     * @return found {@link Tag} represented as {@link TagResponse}
     */
    @UserAllowed
    @GetMapping("/mostUsedTagForUserWithHighestCostOfAllOrders")
    public ResponseEntity<TagResponse> findMostUsedTagForUserWithHighestCostOfAllOrders() {
        Tag tag = tagService.findMostUsedTagForUserWithHighestCostOfAllOrders();
        TagResponse tagResponse = TagMapper.convertToResponse(tag);
        tagResponse.add(linkTo(methodOn(TagController.class)
                .findMostUsedTagForUserWithHighestCostOfAllOrders()).withSelfRel());
        return ResponseEntity.ok(tagResponse);
    }
}
