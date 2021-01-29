package com.epam.esm.repository;

import com.epam.esm.common.entity.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> getAll(Integer page, Integer limit);

    Order get(Long id);

    List<Order> getAllUserOrders(Long userId);

    List<Order> getUserOrders(Long userId, Integer page, Integer limit);

    Order createUserOrder(Order order);
}
