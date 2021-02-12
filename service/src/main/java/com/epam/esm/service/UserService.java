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
     * Retrieve {@link User} by certain id
     *
     * @param id specific user's identifier
     * @return certain {@link User}
     */
    User findById(Long id);

    /**
     * Retrieve list of {@link Order} for certain user in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param id specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Order} for certain user
     */
    List<Order> getUserOrders(Long id, int page, int limit);

    /**
     * Persist new {@link Order} for certain user
     *
     * @param order the object that contains data about new
     *              {@link Order} for certain user
     * @return created {@link Order}
     */
    Order createUserOrder(Long id, Order order);
}
