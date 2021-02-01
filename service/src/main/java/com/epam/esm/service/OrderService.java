package com.epam.esm.service;

import com.epam.esm.common.entity.Order;

import java.util.List;

public interface OrderService {

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
}
