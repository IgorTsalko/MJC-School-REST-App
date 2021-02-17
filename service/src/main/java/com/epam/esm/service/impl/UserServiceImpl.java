package com.epam.esm.service.impl;

import com.epam.esm.common.entity.User;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers(int page, int limit) {
        List<User> users = userRepository
                .findAll(PageRequest.of(page - 1, limit, Sort.by("id")))
                .getContent();
        users.forEach(user -> Hibernate.initialize(user.getOrders()));
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.USER_NOT_FOUND, id));
        Hibernate.initialize(user.getOrders());
        return user;
    }
}
