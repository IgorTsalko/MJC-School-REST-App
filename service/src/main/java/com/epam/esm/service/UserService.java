package com.epam.esm.service;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll(Integer page, Integer limit);

    User get(Long id);

    List<Order> getUserOrders(Long id, Integer page, Integer limit);

    Order createUserOrder(Long id, Order order);
}
