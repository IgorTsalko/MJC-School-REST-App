package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.Tag;
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

import java.util.List;
import java.util.Map;

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
        List<Tag> actualTags = tagRepository.getAllTags();
        assertEquals(expTags, actualTags);
    }

    @Test
    public void getTagById() {
        Tag expTag = new Tag().setId(2L).setName("travel");
        Tag actualTag = tagRepository.getTag(2L);
        assertEquals(expTag, actualTag);
    }

    @Test
    public void getTagByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> tagRepository.getTag(10L));
    }

    @Test
    public void getCertificateTagsByListCertificatesIds() {
        Map<Long, List<Tag>> expCertTags = Map.of(
                1L, List.of(new Tag().setId(1L).setName("incredible")),
                2L, List.of(new Tag().setId(1L).setName("incredible"), new Tag().setId(2L).setName("travel"))
        );
        List<Certificate> certs = List.of(
                new Certificate().setId(1L).setName("Trip"),
                new Certificate().setId(2L).setName("Skydiving")
        );
        Map<Long, List<Tag>> actualCertTags = tagRepository.getCertificatesTags(certs);
        assertEquals(expCertTags, actualCertTags);
    }

    @Test
    public void getCertificateTagsByCertificateId() {
        List<Tag> expTags = getMockTags();
        List<Tag> actualTags = tagRepository.getCertificateTags(2L);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void createNewTag() {
        Tag expTag = new Tag().setId(3L).setName("NewTag");
        Tag newTag = new Tag().setName("NewTag");
        Tag actualTag = tagRepository.createNewTag(newTag);
        assertEquals(expTag, actualTag);
    }

    @Test
    public void createTagsIfNonExist() {
        List<Tag> expTags = List.of(
                new Tag().setId(1L).setName("incredible"),
                new Tag().setId(2L).setName("travel"),
                new Tag().setId(3L).setName("someNewTag1"),
                new Tag().setId(4L).setName("someNewTag2")
        );
        List<Tag> tags = List.of(
                new Tag().setName("incredible"),
                new Tag().setName("someNewTag1"),
                new Tag().setName("someNewTag2")
        );
        tagRepository.createNonExistentTags(tags);
        List<Tag> actualTags = tagRepository.getAllTags();
        assertEquals(expTags, actualTags);
    }

    @Test
    public void deleteTag() {
        List<Tag> expTags = List.of(
                new Tag().setId(2L).setName("travel")
        );
        tagRepository.deleteTag(1L);
        List<Tag> actualTags = tagRepository.getAllTags();
        assertEquals(expTags, actualTags);
    }

    @Test
    public void deleteTagByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> tagRepository.deleteTag(10L));
    }

    private List<Tag> getMockTags() {
        return List.of(
                new Tag().setId(1L).setName("incredible"),
                new Tag().setId(2L).setName("travel")
        );
    }
}
