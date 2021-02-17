package com.epam.esm.repository;

import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserRepository {

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

    /**
     * Retrieve {@link User} by certain login or return null
     *
     * @param login specific user's login
     * @return certain {@link User} or null if user does not exist
     */
    User findByLogin(String login);

    /**
     * Persist a new {@link User}
     *
     * @param user the object that contain properties for new {@link User}
     */
    void create(User user);
}
