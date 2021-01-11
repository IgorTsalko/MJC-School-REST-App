package com.epam.esm.server;

import com.epam.esm.server.entity.TagRequest;
import com.epam.esm.server.entity.TagResponse;
import com.epam.esm.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tags")
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
    public TagResponse getTag(@PathVariable int id) {
        return TagMapper.dtoToResponse(tagService.getTag(id));
    }

    @PostMapping
    public ResponseEntity<TagResponse> createNewTag(@RequestBody TagRequest tagRequest) {
        TagResponse tagResponse = TagMapper.dtoToResponse(tagService.createNewTag(TagMapper.requestToDto(tagRequest)));
        return ResponseEntity.ok(tagResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
