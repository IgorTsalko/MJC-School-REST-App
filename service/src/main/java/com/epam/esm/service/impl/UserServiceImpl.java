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
@Transactional
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
    public List<User> getUsers(int page, int limit) {
        return userRepository.findUsers(page, limit);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Order> getUserOrders(Long id, int page, int limit) {
        return orderRepository.retrieveUserOrders(id, page, limit);
    }

    @Override
    public Order createUserOrder(Long userId, Order order) {
        Certificate certificate = certificateRepository.findById(order.getCertificateId());
        order.setPrice(certificate.getPrice());
        order.setUserId(userId);
        return orderRepository.save(order);
    }
}
