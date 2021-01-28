package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.getAll();
        users.forEach(user -> user.setOrders(null));
        return users;
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id)
                .setOrders(orderRepository.getUserOrders(id));
    }

    @Override
    public List<Order> getUserOrders(Long id) {
        return orderRepository.getUserOrders(id);
    }
}
