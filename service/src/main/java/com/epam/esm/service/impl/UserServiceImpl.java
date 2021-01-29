package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;

    public UserServiceImpl(UserRepository userRepository,
                           OrderRepository orderRepository,
                           CertificateRepository certificateRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public List<User> getAll(Integer page, Integer limit) {
        return userRepository.getAll(page, limit);
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id)
                .setOrders(orderRepository.getAllUserOrders(id));
    }

    @Override
    public List<Order> getUserOrders(Long id, Integer page, Integer limit) {
        return orderRepository.getUserOrders(id, page, limit);
    }

    @Transactional
    @Override
    public Order createUserOrder(Long userId, Order order) {
        Certificate certificate = certificateRepository.get(order.getCertificateId());
        order.setPrice(certificate.getPrice());
        order.setUserId(userId);
        return orderRepository.createUserOrder(order);
    }
}
