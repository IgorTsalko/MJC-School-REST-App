package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    TagService tagService;
    @Mock
    TagRepository tagRepository;

    @Test
    public void getAllTags() {
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("travel")
        );
        when(tagRepository.getAllTags()).thenReturn(expTags);
        List<TagDTO> realTags = tagService.getAllTags();

        assertEquals(expTags, realTags);
        verify(tagRepository, only()).getAllTags();
    }

    @Test
    public void getTagById() {
        TagDTO expTag = new TagDTO().setId(1).setName("incredible");
        when(tagRepository.getTag(anyInt())).thenReturn(expTag);

        TagDTO realTag = tagService.getTag(1);

        assertEquals(expTag, realTag);
        verify(tagRepository, only()).getTag(anyInt());
    }
}
