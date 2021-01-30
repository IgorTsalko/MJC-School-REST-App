package com.epam.esm.repository;

import com.epam.esm.common.entity.Tag;

import java.util.List;

public interface TagRepository {

    /**
     * Retrieve all <code>Tags</code>.
     *
     * @return list of <code>Tags</code>
     */
    List<Tag> getTags(int page, int limit);

    /**
     * Retrieve certain <code>Tag</code> for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain <code>Tag</code>
     */
    Tag get(Long id);

    /**
     * Retrieves certificate connections with matching tags for appropriate <code>Certificate</code>
     *
     * @param certificateId specific certificate's identifier
     * @return list of tags for the appropriate <code>Certificate</code>
     */
    List<Tag> getCertificateTags(Long certificateId);

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tag the object that contain properties for new <code>Tag</code>
     * @return created <code>Tag</code>
     */
    Tag create(Tag tag);

    /**
     * Create new tags by tag's names if any tags are not exist
     *
     * @param tags list of tags
     */
    List<Tag> createNonExistent(List<Tag> tags);

    /**
     * Delete certain <code>Tag</code>
     *
     * @param id specific tag's identifier
     */
    void delete(Long id);
}
