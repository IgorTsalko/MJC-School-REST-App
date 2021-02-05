package com.epam.esm.service;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserService {

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

    /**
     * Retrieve list of <code>Orders</code> for certain <code>User</code> in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param id specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Orders</code> for certain <code>User</code>
     */
    List<Order> getUserOrders(Long id, int page, int limit);

    /**
     * Persist new <code>Order</code> for certain <code>User</code>
     *
     * @param order the object that contains data about new
     *              <code>Order</code> for certain <code>User</code>
     * @return created <code>Order</code>
     */
    Order createUserOrder(Long id, Order order);
}
