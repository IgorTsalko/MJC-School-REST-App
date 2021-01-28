package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
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
                new Tag().setId(1L).setName("incredible"),
                new Tag().setId(2L).setName("travel")
        );
        when(tagRepository.getAll()).thenReturn(expTags);
        List<Tag> actualTags = tagService.getAll();

        assertEquals(expTags, actualTags);
        verify(tagRepository, only()).getAll();
    }

    @Test
    public void getTagById() {
        Tag expTag = new Tag().setId(1L).setName("incredible");
        when(tagRepository.get(anyLong())).thenReturn(expTag);

        Tag actualTag = tagService.get(1L);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).get(anyLong());
    }

    @Test
    public void createNewTag(@Mock Tag tag) {
        Tag expTag = new Tag().setId(1L).setName("incredible");
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
