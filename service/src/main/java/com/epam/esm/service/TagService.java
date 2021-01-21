package com.epam.esm.service;

import com.epam.esm.common.Tag;

import java.util.List;

public interface TagService {

    /**
     * Retrieve all <code>Tags</code>.
     *
     * @return list of <code>Tags</code>
     */
    List<Tag> getAllTags();

    /**
     * Retrieve certain <code>Tag</code> for appropriate id.
     *
     * @param id specific tag's identifier
     * @return certain <code>Tag</code>
     */
    Tag getTag(Long id);

    /**
     * Create new <code>Tag</code> and return it
     *
     * @param tag the object that contain properties for new <code>Tag</code>
     * @return created <code>Tag</code>
     */
    Tag createNewTag(Tag tag);

    /**
     * Delete certain <code>Tag</code>
     *
     * @param id specific tag's identifier
     */
    void deleteTag(Long id);
}
