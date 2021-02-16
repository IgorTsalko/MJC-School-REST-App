package com.epam.esm.service.impl;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.Order;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public List<Order> getOrders(int page, int limit) {
        return orderRepository.getOrders(page, limit);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order create(Long userId, Order order) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(order.getGiftCertificateId());
        order.setPrice(giftCertificate.getPrice());
        order.setUserId(userId);
        return orderRepository.create(order);
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId, int page, int limit) {
        return orderRepository.findOrdersByUserId(userId, page, limit);
    }
}
