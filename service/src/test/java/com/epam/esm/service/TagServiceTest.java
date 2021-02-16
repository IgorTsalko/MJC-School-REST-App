package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    TagServiceImpl tagService;
    @Mock
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllTags() {
        List<Tag> expTags = List.of(
                new Tag().setId(1L).setTitle("incredible"),
                new Tag().setId(2L).setTitle("travel")
        );
        when(tagRepository.getTags(1, 20)).thenReturn(expTags);
        List<Tag> actualTags = tagService.getTags(1, 20);

        assertEquals(expTags, actualTags);
        verify(tagRepository, only()).getTags(1, 20);
    }

    @Test
    public void getTagById() {
        Tag expTag = new Tag().setId(1L).setTitle("incredible");
        when(tagRepository.findById(anyLong())).thenReturn(expTag);

        Tag actualTag = tagService.findById(1L);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).findById(anyLong());
    }

    @Test
    public void createNewTag(@Mock Tag tag) {
        Tag expTag = new Tag().setId(1L).setTitle("incredible");
        when(tagRepository.create(tag)).thenReturn(expTag);

        Tag actualTag = tagService.create(tag);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).create(tag);
    }

    @Test
    public void deleteTag() {
        tagService.delete(anyLong());
        verify(tagRepository, only()).delete(anyLong());
    }
}
