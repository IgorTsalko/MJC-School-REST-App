package com.epam.esm.repository;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.Tag;

import java.util.List;

public interface TagRepository {

    /**
     * Retrieve list of {@link Tag} in an amount equal to the
     * <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Tag}
     */
    List<Tag> retrieveTags(int page, int limit);

    /**
     * Retrieve certain {@link Tag} for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain {@link Tag}
     */
    Tag findById(Long id);

    /**
     * Retrieves certificate connections with matching tags for appropriate {@link Certificate}
     *
     * @param certificateId specific certificate's identifier
     * @return list of tags for the appropriate {@link Certificate}
     */
    List<Tag> retrieveCertificateTags(Long certificateId);

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tag the object that contain properties for new {@link Tag}
     * @return created {@link Tag}
     */
    Tag save(Tag tag);

    /**
     * Create new tags by tag's names if any tags are not exist
     *
     * @param tags list of {@link Tag}
     */
    List<Tag> saveNonExistent(List<Tag> tags);

    /**
     * Delete certain {@link Tag}
     *
     * @param id specific tag's identifier
     */
    void delete(Long id);

    /**
     * Find the most widely used {@link Tag} of a user with the highest
     * cost of all orders
     *
     * @return found {@link Tag}
     */
    Tag findMostUsedTagForUserWithHighestCostOfAllOrders();
}
