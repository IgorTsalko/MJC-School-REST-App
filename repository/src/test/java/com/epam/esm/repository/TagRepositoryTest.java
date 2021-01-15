package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.TagDTO;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.config.RepositoryConfigTest;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = RepositoryConfigTest.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TagRepositoryTest {

    @Autowired
    TagRepositoryImpl tagRepository;

    @Test
    public void getAllTags() {
        List<TagDTO> expTags = getMockTags();
        List<TagDTO> actualTags = tagRepository.getAllTags();
        assertEquals(expTags, actualTags);
    }

    @Test
    public void getTagById() {
        TagDTO expTag = new TagDTO().setId(2).setName("travel");
        TagDTO actualTag = tagRepository.getTag(2);
        assertEquals(expTag, actualTag);
    }

    @Test
    public void getTagByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> tagRepository.getTag(10));
    }

    @Test
    public void getCertificateTagsByListCertificatesIds() {
        Map<Integer, List<TagDTO>> expCertTags = Map.of(
                1, List.of(new TagDTO().setId(1).setName("incredible")),
                2, List.of(new TagDTO().setId(1).setName("incredible"), new TagDTO().setId(2).setName("travel"))
        );
        List<CertificateDTO> certs = List.of(
                new CertificateDTO().setId(1).setName("Trip"),
                new CertificateDTO().setId(2).setName("Skydiving")
        );
        Map<Integer, List<TagDTO>> actualCertTags = tagRepository.getCertificatesTags(certs);
        assertEquals(expCertTags, actualCertTags);
    }

    @Test
    public void getCertificateTagsByCertificateId() {
        List<TagDTO> expTags = getMockTags();
        List<TagDTO> actualTags = tagRepository.getCertificateTags(2);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void getTagsByName() {
        List<TagDTO> expTags = getMockTags();
        List<TagDTO> tags = List.of(
                new TagDTO().setName("incredible"),
                new TagDTO().setName("travel")
        );
        List<TagDTO> actualTags = tagRepository.getTagsByName(tags);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void createNewTag() {
        TagDTO expTag = new TagDTO().setId(3).setName("NewTag");
        TagDTO newTag = new TagDTO().setName("NewTag");
        TagDTO actualTag = tagRepository.createNewTag(newTag);
        assertEquals(expTag, actualTag);
    }

    @Test
    public void createNewTags() {
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(3).setName("someNewTag1"),
                new TagDTO().setId(4).setName("someNewTag2")
        );
        List<TagDTO> tags = List.of(
                new TagDTO().setName("someNewTag1"),
                new TagDTO().setName("someNewTag2")
        );
        tagRepository.createNewTags(tags);
        List<TagDTO> actualTags = tagRepository.getTagsByName(tags);
        assertEquals(expTags, actualTags);
    }

    @Test
    public void createTagsIfNonExist() {
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("travel"),
                new TagDTO().setId(3).setName("someNewTag1"),
                new TagDTO().setId(4).setName("someNewTag2")
        );
        List<TagDTO> tags = List.of(
                new TagDTO().setName("incredible"),
                new TagDTO().setName("someNewTag1"),
                new TagDTO().setName("someNewTag2")
        );
        tagRepository.createTagsIfNonExist(tags);
        List<TagDTO> actualTags = tagRepository.getAllTags();
        assertEquals(expTags, actualTags);
    }

    @Test
    public void deleteTag() {
        List<TagDTO> expTags = List.of(
                new TagDTO().setId(2).setName("travel")
        );
        tagRepository.deleteTag(1);
        List<TagDTO> actualTags = tagRepository.getAllTags();
        assertEquals(expTags, actualTags);
    }

    @Test
    public void deleteTagByNotExistentId() {
        assertThrows(EntityNotFoundException.class, () -> tagRepository.deleteTag(10));
    }

    private List<TagDTO> getMockTags() {
        return List.of(
                new TagDTO().setId(1).setName("incredible"),
                new TagDTO().setId(2).setName("travel")
        );
    }
}
