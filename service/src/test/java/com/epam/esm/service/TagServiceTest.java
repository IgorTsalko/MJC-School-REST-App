package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    TagServiceImpl tagService;
    @Mock
    TagRepository tagRepository;

    @Test
    public void getTags(@Mock Page<Tag> page) {
        List<Tag> expTags = List.of(
                new Tag().setId(1L).setTitle("incredible"),
                new Tag().setId(2L).setTitle("travel")
        );
        when(tagRepository.findAll(PageRequest.of(0, 20, Sort.by("id")))).thenReturn(page);
        when(page.getContent()).thenReturn(expTags);
        List<Tag> actualTags = tagService.getTags(1, 20);

        assertEquals(expTags, actualTags);
        verify(tagRepository, only()).findAll(PageRequest.of(0, 20, Sort.by("id")));
    }

    @Test
    public void getTagById() {
        Tag expTag = new Tag().setId(1L).setTitle("incredible");
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(expTag));

        Tag actualTag = tagService.findById(1L);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).findById(anyLong());
    }

    @Test
    public void createNewTag(@Mock Tag tag) {
        Tag expTag = new Tag().setId(1L).setTitle("incredible");
        when(tagRepository.save(tag)).thenReturn(expTag);

        Tag actualTag = tagService.create(tag);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).save(tag);
    }

    @Test
    public void deleteTag() {
        when(tagRepository.existsById(anyLong())).thenReturn(true);

        tagService.delete(anyLong());

        verify(tagRepository).existsById(anyLong());
        verify(tagRepository).deleteById(anyLong());
        verifyNoMoreInteractions(tagRepository);
    }
}
