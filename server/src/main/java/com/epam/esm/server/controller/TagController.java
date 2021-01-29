package com.epam.esm.server.controller;

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
import javax.validation.constraints.Positive;
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
     * Retrieve all <code>Tags</code>.
     *
     * @return list of <code>Tags</code>
     */
    @GetMapping
    public CollectionModel<TagResponse> getAll(@RequestParam(required = false) @Positive Integer page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Positive Integer limit) {
        List<TagResponse> tags = tagService.getAll(page, limit)
                .stream().map(TagMapper::convertToResponse)
                .collect(Collectors.toList());
        tags.forEach(t -> t.add(linkTo(methodOn(TagController.class).get(t.getId())).withSelfRel()));

        List<Link> links = new ArrayList<>();
        if (page == null) {
            links.add(linkTo(methodOn(TagController.class).getAll(null, null)).withSelfRel().expand());
        } else {
            links.add(linkTo(methodOn(TagController.class).getAll(page, limit)).withSelfRel());
        }

        links.add(linkTo(methodOn(TagController.class).getAll(1, limit)).withRel("first"));

        if (page != null) {
            links.add(linkTo(methodOn(TagController.class).getAll(page + 1, limit)).withRel("next"));
            if (page > 1) {
                links.add(linkTo(methodOn(TagController.class).getAll(page - 1, limit)).withRel("previous"));
            }
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
        TagResponse tagResponse = TagMapper.convertToResponse(tagService.create(TagMapper.convertToEntity(tagRequest)));
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
}
