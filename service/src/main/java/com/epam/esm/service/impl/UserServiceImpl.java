package com.epam.esm.service.impl;

import com.epam.esm.common.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(int page, int limit) {
        return userRepository.getUsers(page, limit);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
