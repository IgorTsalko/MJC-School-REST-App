package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.config.RepositoryConfigTest;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = RepositoryConfigTest.class)
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TagRepositoryTest {

    @Autowired
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllTags() {
        List<Tag> expTags = getMockTags();
        List<Tag> actualTags = tagRepository.retrieveTags(1, 20);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void getTagById() {
        Tag expTag = new Tag().setId(2L).setTitle("travel");
        Tag actualTag = tagRepository.findById(2L);
        assertEquals(expTag, actualTag);
    }

    @Test
    public void getTagByNonExistentId() {
        assertThrows(EntityNotFoundException.class, () -> tagRepository.findById(10L));
    }

    @Test
    public void getCertificateTagsByCertificateId() {
        List<Tag> expTags = getMockTags();
        List<Tag> actualTags = tagRepository.retrieveCertificateTags(2L);
        assertEquals(expTags, actualTags);
    }

    @Test
    @Transactional
    public void createNewTag() {
        Tag expTag = new Tag().setId(3L).setTitle("NewTag");
        Tag newTag = new Tag().setTitle("NewTag");
        Tag actualTag = tagRepository.save(newTag);
        assertEquals(expTag, actualTag);
    }

    @Test
    @Transactional
    public void createNonExistentTags() {
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
        tagRepository.saveNonExistent(tags);
        List<Tag> actualTags = tagRepository.retrieveTags(1, 20);
        assertEquals(expTags, actualTags);
    }

    @Test
    @Transactional
    public void deleteTagById() {
        List<Tag> expTags = List.of(
                new Tag().setId(2L).setTitle("travel")
        );
        tagRepository.delete(1L);
        List<Tag> actualTags = tagRepository.retrieveTags(1, 20);
        assertEquals(expTags, actualTags);
    }

    @Test
    @Transactional
    public void deleteTagByNonExistentId() {
        assertThrows(EntityNotFoundException.class, () -> tagRepository.delete(10L));
    }

    private List<Tag> getMockTags() {
        return List.of(
                new Tag().setId(1L).setTitle("incredible"),
                new Tag().setId(2L).setTitle("travel")
        );
    }
}
