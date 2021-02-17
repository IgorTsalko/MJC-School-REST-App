package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.repository.config.RepositoryConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = RepositoryConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Transactional
public class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    public void saveIfNotExist() {
        List<Tag> expTags = List.of(
                new Tag().setId(1L).setTitle("incredible"),
                new Tag().setId(2L).setTitle("travel"),
                new Tag().setId(3L).setTitle("someNewTag1"),
                new Tag().setId(4L).setTitle("someNewTag2")
        );
        List<Tag> tags = List.of(
                new Tag().setTitle("incredible"),
                new Tag().setTitle("someNewTag1"),
                new Tag().setTitle("someNewTag2")
        );
        tagRepository.saveIfNotExist(tags);
        List<Tag> actualTags = tagRepository.findAll();
        assertEquals(expTags, actualTags);
    }
}
