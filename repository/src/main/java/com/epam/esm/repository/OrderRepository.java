package com.epam.esm.repository;

import com.epam.esm.common.entity.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> getOrders(int page, int limit);

    Order get(Long id);

    List<Order> getAllUserOrders(Long userId);

    List<Order> getUserOrders(Long userId, int page, int limit);

    Order createUserOrder(Order order);
}
