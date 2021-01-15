package com.epam.esm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.esm.common.TagDTO;
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
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("travel")
        );
        when(tagRepository.getAllTags()).thenReturn(expTags);
        List<TagDTO> actualTags = tagService.getAllTags();

        assertEquals(expTags, actualTags);
        verify(tagRepository, only()).getAllTags();
    }

    @Test
    public void getTagById() {
        TagDTO expTag = new TagDTO().setId(1).setName("incredible");
        when(tagRepository.getTag(anyInt())).thenReturn(expTag);

        TagDTO actualTag = tagService.getTag(1);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).getTag(anyInt());
    }

    @Test
    public void createNewTag(@Mock TagDTO tagDTO) {
        TagDTO expTag = new TagDTO().setId(1).setName("incredible");
        when(tagRepository.createNewTag(tagDTO)).thenReturn(expTag);

        TagDTO actualTag = tagService.createNewTag(tagDTO);

        assertEquals(expTag, actualTag);
        verify(tagRepository, only()).createNewTag(tagDTO);
    }

    @Test
    public void deleteTag() {
        tagService.deleteTag(anyInt());
        verify(tagRepository, only()).deleteTag(anyInt());
    }
}
