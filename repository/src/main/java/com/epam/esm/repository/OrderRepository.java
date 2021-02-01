package com.epam.esm.repository;

import com.epam.esm.common.entity.Order;

import java.util.List;

public interface OrderRepository {

    /**
     * Retrieve list of <code>Orders</code> for appropriate id in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Orders</code>
     */
    List<Order> getOrders(int page, int limit);

    /**
     * Retrieve <code>Order</code> by certain id
     *
     * @param id specific order's identifier
     * @return certain <code>Order</code>
     */
    Order get(Long id);

    /**
     * Retrieve list of all <code>Orders</code> for certain <code>User</code>
     *
     * @param userId specific user's identifier
     * @return list of <code>Orders</code> for certain <code>User</code>
     */
    List<Order> getAllUserOrders(Long userId);

    /**
     * Retrieve list of <code>Orders</code> for certain <code>User</code> in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param userId specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Orders</code> for certain <code>User</code>
     */
    List<Order> getUserOrders(Long userId, int page, int limit);

    /**
     * Persist new <code>Order</code> for certain <code>User</code>
     *
     * @param order the object that contains data about new
     *              <code>Order</code> for certain <code>User</code>
     * @return created <code>Order</code>
     */
    Order createUserOrder(Order order);
}
