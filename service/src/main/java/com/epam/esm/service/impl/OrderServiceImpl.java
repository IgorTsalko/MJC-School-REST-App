package com.epam.esm.service.impl;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.Order;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrders(int page, int limit) {
        return orderRepository
                .findAll(PageRequest.of(page - 1, limit, Sort.by("orderId")))
                .getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.ORDER_NOT_FOUND, id));
    }

    @Override
    @Transactional
    public Order create(Long userId, Order order) {
        GiftCertificate giftCertificate = giftCertificateRepository
                .findById(order.getGiftCertificateId())
                .orElseThrow(() ->
                        new EntityNotFoundException(ErrorDefinition.GIFT_CERTIFICATE_NOT_FOUND, order.getGiftCertificateId())
                );

        order.setPrice(giftCertificate.getPrice());
        order.setUserId(userId);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersByUserId(Long userId, int page, int limit) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(ErrorDefinition.USER_NOT_FOUND, userId);
        }
        return orderRepository.findOrderByUserId(userId, PageRequest.of(page - 1, limit, Sort.by("orderId")));
    }
}
