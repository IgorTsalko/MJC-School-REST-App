package com.epam.esm.repository;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.TagDTO;

import java.util.List;
import java.util.Map;

public interface TagRepository {

    List<TagDTO> getAllTags();

    TagDTO getTag(int id);

    Map<Integer, List<TagDTO>> getCertificatesTags(List<CertificateDTO> certificates);

    List<TagDTO> getCertificateTags(int certificateId);

    List<TagDTO> getTagsByName(List<TagDTO> tagDTOList);

    TagDTO createNewTag(TagDTO tag);

    void createNewTags(List<TagDTO> tags);

    void createTagsIfNonExist(List<TagDTO> tags);

    void deleteTag(int id);
}
