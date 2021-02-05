package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.server.mapper.TagMapper;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;
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
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/tags")
@Validated
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Retrieve all <code>Tags</code> in an amount equal to the
     * <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Tags</code>
     */
    @GetMapping
    public CollectionModel<TagResponse> getTags(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<TagResponse> tags = tagService.getTags(pageNumber, limit)
                .stream().map(TagMapper::convertToResponse)
                .collect(Collectors.toList());
        tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TagController.class).getTags(page, limit)).withSelfRel());
        links.add(linkTo(methodOn(TagController.class).getTags(1, limit)).withRel("first"));

        if (page > 0 && tags.size() == limit) {
            links.add(linkTo(methodOn(TagController.class).getTags(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(TagController.class).getTags(page - 1, limit)).withRel("previous"));
        }

        return CollectionModel.of(tags, links);
    }

    /**
     * Retrieve certain <code>Tag</code> for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain <code>Tag</code>
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> get(@PathVariable @Positive Long id) {
        TagResponse tagResponse = TagMapper.convertToResponse(tagService.get(id));
        tagResponse.add(linkTo(methodOn(TagController.class).get(id)).withSelfRel());
        return ResponseEntity.ok(tagResponse);
    }

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tagRequest the object that contain properties for new <code>Tag</code>
     * @return created <code>Tag</code>
     */
    @PostMapping
    public ResponseEntity<TagResponse> create(@RequestBody @Valid TagRequest tagRequest) {
        Tag tag = tagService.create(TagMapper.convertToEntity(tagRequest));
        TagResponse tagResponse = TagMapper.convertToResponse(tag);
        tagResponse.add(linkTo(methodOn(TagController.class).create(tagRequest)).withSelfRel());
        return ResponseEntity.ok(tagResponse);
    }

    /**
     * Delete certain <code>Tag</code>
     *
     * @param id specific tag's identifier
     * @return successful status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find the most widely used <code>Tag</code> of a user with the highest
     * cost of all orders
     *
     * @return found <code>Tag</code>
     */
    @GetMapping("/mostUsedTagForUserWithHighestCostOfAllOrders")
    public ResponseEntity<TagResponse> findMostUsedTagForUserWithHighestCostOfAllOrders() {
        Tag tag = tagService.findMostUsedTagForUserWithHighestCostOfAllOrders();
        TagResponse tagResponse = TagMapper.convertToResponse(tag);
        tagResponse.add(linkTo(methodOn(TagController.class)
                .findMostUsedTagForUserWithHighestCostOfAllOrders()).withSelfRel());
        return ResponseEntity.ok(tagResponse);
    }
}
