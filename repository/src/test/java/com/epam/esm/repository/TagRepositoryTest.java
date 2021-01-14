package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.epam.esm.common.TagDTO;
import com.epam.esm.repository.config.RepositoryConfigTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ContextConfiguration(classes = RepositoryConfigTest.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    public void retrieveAllTags() {
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("travel")
        );
        List<TagDTO> realTags = tagRepository.getAllTags();

        assertEquals(expTags, realTags);
    }

    @Test
    public void retrieveTagById() {
        TagDTO expTag = new TagDTO().setId(2).setName("travel");
        TagDTO realTag = tagRepository.getTag(2);
        assertEquals(expTag, realTag);
    }
}
