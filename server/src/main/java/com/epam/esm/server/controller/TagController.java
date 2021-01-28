package com.epam.esm.server.controller;

import com.epam.esm.server.mapper.TagMapper;
import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;
import com.epam.esm.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tags")
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
    public List<TagResponse> getAll() {
        return tagService.getAll().stream().map(TagMapper::convertToResponse).collect(Collectors.toList());
    }

    /**
     * Retrieve certain <code>Tag</code> for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain <code>Tag</code>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(TagMapper.convertToResponse(tagService.get(id)));
    }

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tagRequest the object that contain properties for new <code>Tag</code>
     * @return created <code>Tag</code>
     */
    @PostMapping
    public ResponseEntity<TagResponse> create(@RequestBody @Valid TagRequest tagRequest) {
        TagResponse tagResponse =
                TagMapper.convertToResponse(tagService.create(TagMapper.convertToEntity(tagRequest)));
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
