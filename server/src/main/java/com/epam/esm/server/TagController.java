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

    @GetMapping
    public List<TagResponse> getAllTags() {
        return tagService.getAllTags().stream().map(TagMapper::dtoToResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTag(@PathVariable @Positive int id) {
        return ResponseEntity.ok(TagMapper.dtoToResponse(tagService.getTag(id)));
    }

    @PostMapping
    public ResponseEntity<TagResponse> createNewTag(@RequestBody @Valid TagRequest tagRequest) {
        TagResponse tagResponse = TagMapper.dtoToResponse(tagService.createNewTag(TagMapper.requestToDto(tagRequest)));
        return ResponseEntity.ok(tagResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @Positive int id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
