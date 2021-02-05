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
    List<User> getUsers(int page, int limit);

    /**
     * Retrieve <code>User</code> by certain id
     *
     * @param id specific user's identifier
     * @return certain <code>User</code>
     */
    User get(Long id);
}
