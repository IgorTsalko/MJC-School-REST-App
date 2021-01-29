package com.epam.esm.service;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(Long id);

    List<Order> getUserOrders(Long id);
}
