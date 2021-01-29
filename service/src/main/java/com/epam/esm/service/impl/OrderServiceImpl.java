package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll(Integer page, Integer limit) {
        return orderRepository.getAll(page, limit);
    }

    @Override
    public Order get(Long id) {
        return orderRepository.get(id);
    }
}