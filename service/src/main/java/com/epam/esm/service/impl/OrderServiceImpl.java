package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrders(int page, int limit) {
        return orderRepository.retrieveOrders(page, limit);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id);
    }
}
