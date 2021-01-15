package com.epam.esm.server;

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
    public List<TagResponse> getAllTags() {
        return tagService.getAllTags().stream().map(TagMapper::convertToResponse).collect(Collectors.toList());
    }

    /**
     * Retrieve certain <code>Tag</code> for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain <code>Tag</code>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTag(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(TagMapper.convertToResponse(tagService.getTag(id)));
    }

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tagRequest the object that contain properties for new <code>Tag</code>
     * @return created <code>Tag</code>
     */
    @PostMapping
    public ResponseEntity<TagResponse> createNewTag(@RequestBody @Valid TagRequest tagRequest) {
        TagResponse tagResponse =
                TagMapper.convertToResponse(tagService.createNewTag(TagMapper.convertToEntity(tagRequest)));
        return ResponseEntity.ok(tagResponse);
    }

    /**
     * Delete certain <code>Tag</code>
     *
     * @param id specific tag's identifier
     * @return successful status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @Positive Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
