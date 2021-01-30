package com.epam.esm.service;

import com.epam.esm.common.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(int page, int limit);

    Order get(Long id);
}
