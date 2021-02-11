package com.epam.esm.repository;

import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserRepository {

    /**
     * Retrieve <code>Users</code> for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Users</code>
     */
    List<User> findUsers(int page, int limit);

    /**
     * Retrieve <code>User</code> by certain id
     *
     * @param id specific user's identifier
     * @return certain <code>User</code>
     */
    User findById(Long id);

    /**
     * Retrieve <code>User</code> by certain login
     *
     * @param login specific user's login
     * @return certain <code>User</code>
     */
    User findByLogin(String login);

    /**
     * Sign up new <code>User</code>
     *
     * @param user the object that contain properties for new <code>User</code>
     */
    void save(User user);
}
