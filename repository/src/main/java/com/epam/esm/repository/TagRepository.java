package com.epam.esm.repository;

import com.epam.esm.common.entity.GiftCertificate;
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
    List<Tag> getTags(int page, int limit);

    /**
     * Find {@link Tag} by <code>id</code> and return it
     *
     * @param id specific tag's identifier
     * @return certain {@link Tag}
     */
    Tag findById(Long id);

    /**
     * Retrieve certificate connections with matching tags for appropriate {@link GiftCertificate}
     *
     * @param certificateId specific certificate's identifier
     * @return list of tags for the appropriate {@link GiftCertificate}
     */
    List<Tag> getCertificateTags(Long certificateId);

    /**
     * Persist new {@link Tag} and return it
     *
     * @param tag the object that contain properties for new <code>Tag</code>
     * @return created {@link Tag}
     */
    Tag create(Tag tag);

    /**
     * Create new tags by tag's names if any tags are not exist
     *
     * @param tags list of {@link Tag}
     */
    List<Tag> createNonExistent(List<Tag> tags);

    /**
     * Delete {@link Tag} by <code>id</code>
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
