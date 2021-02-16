package com.epam.esm.service;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Retrieve list of {@link User} for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link User}
     */
    List<User> getUsers(int page, int limit);

    /**
     * Find {@link User} by <code>id</code> and return it
     *
     * @param id specific user's identifier
     * @return certain {@link User}
     */
    User findById(Long id);
}
