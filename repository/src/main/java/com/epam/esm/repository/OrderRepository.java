package com.epam.esm.repository;

import com.epam.esm.common.entity.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> getAll();

    Order get(Long id);

    List<Order> getUserOrders(Long userId);

    Order createUserOrder(Order order);
}
