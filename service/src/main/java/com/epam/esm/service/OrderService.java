package com.epam.esm.service;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;

import java.util.List;

public interface OrderService {

    /**
     * Retrieve list of {@link Order} for appropriate id in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param page  number of page
     * @param limit number of entities in the response
     * @return list of {@link Order}
     */
    List<Order> getOrders(int page, int limit);

    /**
     * Find {@link Order} by <code>id</code>
     *
     * @param id specific order's identifier
     * @return certain {@link Order}
     */
    Order findById(Long id);

    /**
     * Persist new {@link Order} for certain {@link User}
     *
     * @param userId specific user's identifier
     * @param order  the object that contains data about new
     *               {@link Order} for certain user
     * @return created {@link Order}
     */
    Order create(Long userId, Order order);

    /**
     * Retrieve list of {@link Order} for certain {@link User} in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param userId specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Order} for certain {@link User}
     */
    List<Order> findOrdersByUserId(Long userId, int page, int limit);
}
