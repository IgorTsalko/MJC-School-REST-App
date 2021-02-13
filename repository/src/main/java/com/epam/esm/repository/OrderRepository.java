package com.epam.esm.repository;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;

import java.util.List;

public interface OrderRepository {

    /**
     * Retrieve list of {@link Order} for appropriate id in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Order}
     */
    List<Order> retrieveOrders(int page, int limit);

    /**
     * Retrieve {@link Order} by certain id
     *
     * @param id specific order's identifier
     * @return certain {@link Order}
     */
    Order findById(Long id);

    /**
     * Retrieve list of {@link Order} for certain {@link User} in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param userId specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Order} for certain {@link User}
     */
    List<Order> retrieveUserOrders(Long userId, int page, int limit);

    /**
     * Persist new {@link Order} for certain {@link User}
     *
     * @param order the object that contains data about new
     *              {@link Order} for certain {@link User}
     * @return created {@link Order}
     */
    Order save(Order order);
}
