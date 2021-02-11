package com.epam.esm.service;

import com.epam.esm.common.entity.Tag;

import java.util.List;

public interface TagService {

    /**
     * Retrieve all <code>Tags</code> in an amount equal to the
     * <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Tags</code>
     */
    List<Tag> getTags(int page, int limit);

    /**
     * Retrieve certain <code>Tag</code> for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain <code>Tag</code>
     */
    Tag findById(Long id);

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tag the object that contain properties for new <code>Tag</code>
     * @return created <code>Tag</code>
     */
    Tag create(Tag tag);

    /**
     * Delete certain <code>Tag</code>
     *
     * @param id specific tag's identifier
     */
    void delete(Long id);

    /**
     * Find the most widely used <code>Tag</code> of a user with the highest
     * cost of all orders
     *
     * @return found <code>Tag</code>
     */
    Tag findMostUsedTagForUserWithHighestCostOfAllOrders();
}
